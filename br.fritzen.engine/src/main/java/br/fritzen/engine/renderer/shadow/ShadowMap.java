package br.fritzen.engine.renderer.shadow;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL30.*;

import static org.lwjgl.opengl.GL32.*;


public class ShadowMap {

	public static final int SHADOW_SIZE = 1024;
	
    public static final int SHADOW_MAP_WIDTH = SHADOW_SIZE;

    public static final int SHADOW_MAP_HEIGHT = SHADOW_SIZE;

    private final int depthMapFBO;

    private final ShadowTexture depthMap;

    int renderBuffer;
    
    public ShadowMap() throws Exception {
       
    	// Create a FBO to render the depth map
        depthMapFBO = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, depthMapFBO);
        // Set only depth
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        
        // Create the depth map texture
        depthMap = new ShadowTexture(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT, GL_DEPTH_COMPONENT);

        // Attach the the depth map texture to the FBO
        glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, depthMap.getId(), 0);
        //glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, depthMap.getId(), 0);
       
        
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw new Exception("Could not create FrameBuffer");
        }

        // Unbind
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

    }

    
    public ShadowTexture getDepthMapTexture() {
        return depthMap;
    }

    
    public int getDepthMapFBO() {
        return depthMapFBO;
    }

    
    public void cleanup() {
        glDeleteFramebuffers(depthMapFBO);
        depthMap.cleanup();
    }
}