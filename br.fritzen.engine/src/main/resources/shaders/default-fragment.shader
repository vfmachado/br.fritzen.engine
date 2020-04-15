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


float calcShadow() {
    
    /* https://learnopengl.com/Advanced-Lighting/Shadows/Shadow-Mapping
    vec3 projCoords = position.xyz;
    // Transform from screen coordinates to texture coordinates
    projCoords = projCoords * 0.5 + 0.5;
    float bias = 0.05;

    float shadowFactor = 0.0;
    vec2 inc = 1.0 / textureSize(shadowMap, 0);
    for(int row = -1; row <= 1; ++row)
    {
        for(int col = -1; col <= 1; ++col)
        {
            float textDepth = texture(shadowMap, projCoords.xy + vec2(row, col) * inc).r; 
            shadowFactor += projCoords.z - bias > textDepth ? 1.0 : 0.0;        
        }    
    }
    shadowFactor /= 9.0;

    if(projCoords.z > 1.0)
    {
        shadowFactor = 1.0;
    }

    return 1 - shadowFactor;
    */
    
    //bennybox
    vec3 shadowCoords = (shadowMapCoords.xyz/shadowMapCoords.w) * 0.5 + 0.5;	//perspective divide	//transform to 0 to 1
    
    float factor = step(shadowCoords.z, texture(shadowMap, shadowCoords.xy).r); 
    
    return 1 - factor;
    
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
	
		
		linearColor += (
			ambient * u_Material.ambientColor.rgb +
			diffuse * u_Material.diffuseColor.rgb + 
			specular * u_Material.specularColor.rgb
			);
			
	}

	linearColor *= calcShadow();
	
	//extract material alpha from diffuse values
	float alpha = textureColor.a * u_Material.diffuseColor.a;
	
	fragColor = vec4(linearColor, alpha);

	//gamma correction. May create a problem with textures being to bright
	float gamma = 2.2;
    fragColor.rgb = pow(fragColor.rgb, vec3(1.0/gamma));
}