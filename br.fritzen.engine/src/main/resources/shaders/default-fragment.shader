#version 330

layout(location = 0) out vec4 fragColor;

in vec2 v_texCoord;
in vec3 v_normal;
in vec3 v_fragPos;

in mat3 tbnMatrix;

uniform vec4 color;
uniform vec4 materialSpecColor;

uniform float textureRepeats = 1;


uniform vec3 u_CameraPosition;
uniform vec3 cameraDir;

uniform float u_shininess;


uniform int LIGHT_MODEL;

struct Material {
	
	vec4 ambientColor;
	vec4 diffuseColor;
	vec4 specularColor;
	
	sampler2D diffuseTexture;
	sampler2D normalMapTexture;
	sampler2D specMapTexture;
	
	float specularExponent;
	float shininess;
};

uniform Material u_Material;


struct Light {
	vec3 ambientColor;
	vec3 diffuseColor;
	vec3 specularColor;
};


struct DirectionalLight {
	vec3 direction;
	Light light;
};

uniform DirectionalLight u_DirectionalLight[10];

uniform int u_NumberOfDirectionalLights;

in vec4 shadowMapCoords;
uniform sampler2D shadowMap;

float shadowBias = 0.0;


float SampleShadowMap(sampler2D shadowMap, vec2 coords, float compare)
{
	return step(compare - shadowBias, texture2D(shadowMap, coords.xy).r);
}

float SampleShadowMapLinear(sampler2D shadowMap, vec2 coords, float compare, vec2 texelSize)
{
	vec2 pixelPos = coords/texelSize + vec2(0.5);
	vec2 fracPart = fract(pixelPos);
	vec2 startTexel = (pixelPos - fracPart) * texelSize;
	
	float blTexel = SampleShadowMap(shadowMap, startTexel, compare);
	float brTexel = SampleShadowMap(shadowMap, startTexel + vec2(texelSize.x, 0.0), compare);
	float tlTexel = SampleShadowMap(shadowMap, startTexel + vec2(0.0, texelSize.y), compare);
	float trTexel = SampleShadowMap(shadowMap, startTexel + texelSize, compare);
	
	float mixA = mix(blTexel, tlTexel, fracPart.y);
	float mixB = mix(brTexel, trTexel, fracPart.y);
	
	return mix(mixA, mixB, fracPart.x);
}


float SampleShadowMapPCF(sampler2D shadowMap, vec2 coords, float compare, vec2 texelSize)
{
	const float NUM_SAMPLES = 3.0f;
	const float SAMPLES_START = (NUM_SAMPLES-1.0f)/2.0f;
	const float NUM_SAMPLES_SQUARED = NUM_SAMPLES*NUM_SAMPLES;

	float result = 0.0f;
	for(float y = -SAMPLES_START; y <= SAMPLES_START; y += 1.0f)
	{
		for(float x = -SAMPLES_START; x <= SAMPLES_START; x += 1.0f)
		{
			vec2 coordsOffset = vec2(x,y)*texelSize;
			result += SampleShadowMapLinear(shadowMap, coords + coordsOffset, compare, texelSize);
		}
	}
	return result/NUM_SAMPLES_SQUARED;
}


float calcShadow() {
    
    vec3 projCoords = shadowMapCoords.xyz/shadowMapCoords.w;
    projCoords = projCoords * 0.5 + 0.5;
    
    
    //vec2 texelSize  = 1.0 / textureSize(shadowMap, 0);
	//return SampleShadowMapPCF(shadowMap, projCoords.xy, projCoords.z, texelSize );
    
    float shadow = 0.0;
    vec2 texelSize = 1.0 / textureSize(shadowMap, 0);
    for(int x = -1; x <= 1; ++x)
    {
        for(int y = -1; y <= 1; ++y)
        {
            float pcfDepth = texture(shadowMap, projCoords.xy + vec2(x, y) * texelSize).r; 
            shadow += projCoords.z - shadowBias > pcfDepth  ? 1.0 : 0.0;        
        }    
    }
    shadow /= 9.0;
    
    // keep the shadow at 0.0 when outside the far_plane region of the light's frustum.
    if(projCoords.z > 1.0)
        shadow = 0.0;
        
    return 1 - shadow;
    
} 


void main() {

	vec4 textureColor = texture(u_Material.diffuseTexture, v_texCoord * textureRepeats);
	
	//if transparent just discard, lights doesn't affect
	if (textureColor.w < 0.2)
		discard;

	//update normal to normalMap 
	vec3 newNormal = tbnMatrix * (255.0/128.0 * texture(u_Material.normalMapTexture, v_texCoord * textureRepeats).xyz - 1);
	vec3 norm = normalize(newNormal);
	vec3 viewDir = normalize(u_CameraPosition - v_fragPos);
	
	//update spec color from specular map texture.
	vec3 specColorMap = texture(u_Material.specMapTexture, v_texCoord * textureRepeats).xyz;
	
	vec3 linearColor = vec3(0);


	for (int i = 0; i < u_NumberOfDirectionalLights && i < 10; i++) {
		
		DirectionalLight currentLight = u_DirectionalLight[i];
		
		//AMBIENT
		vec3 ambient = currentLight.light.ambientColor * textureColor.xyz;
		
		//DIFFUSE
		vec3 lightDir = normalize(-currentLight.direction);	//directional light
		float diff = max(dot(norm, lightDir), 0.0);
		vec3 diffuse = currentLight.light.diffuseColor * textureColor.xyz * diff;
		
		
		//SPECULAR
		float spec = 0;
			
		if (LIGHT_MODEL == 0) {
			//LIGHT MODEL 0 => PHONG
			vec3 reflectDir = reflect(-lightDir, norm);
			spec = pow(max(dot(viewDir, reflectDir), 0.0), u_Material.shininess);
			
		//BLINN-PHONG - https://learnopengl.com/Advanced-Lighting/Advanced-Lighting
		} else if (LIGHT_MODEL == 1) {
			//LIGHT MODEL 1 => BLINN-PHONG
			vec3 halfwayDir = normalize(lightDir + viewDir);
			spec = pow(max(dot(norm, halfwayDir), 0.0),  u_Material.shininess);
			
		}
		
	
		vec3 specular = currentLight.light.specularColor * specColorMap * spec;	//0.5 to decrease the intensity
	
		shadowBias = max(0.01 * (1.0 - dot(norm,  lightDir)), 0.003);
		float shadow = calcShadow();
		
		linearColor += (
			ambient * u_Material.ambientColor.rgb +
			(diffuse * u_Material.diffuseColor.rgb + 
			specular * u_Material.specularColor.rgb) * shadow
			);
			
	}

	
	
	//extract material alpha from diffuse values
	float alpha = textureColor.a * u_Material.diffuseColor.a;
	
	fragColor = vec4(linearColor, alpha);

	//gamma correction. May create a problem with textures being to bright
	float gamma = 2.2;
    fragColor.rgb = pow(fragColor.rgb, vec3(1.0/gamma));
    
  
    
}