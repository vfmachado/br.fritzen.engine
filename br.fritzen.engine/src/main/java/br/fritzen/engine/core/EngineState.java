package br.fritzen.engine.core;

import org.joml.Vector3f;


/** 
 * 
 * Non instantiable class;
 * 
 * This class contains constants to be used by engine /  game
 * 
 * Most part of these are final, so you must change it in code.
 * 
 * Some of them can be used to debug the engine classes, for example, wireframe models
 * 
 * @author fritz
 *
 */
public abstract class EngineState {

	/**
	 * Target UPS of the engine - How much the engine updates in one second
	 */
	public final static int UPS = 30; 
	
	/**
	 * Target FPS of the engine - Uniforms are updated just in render time.
	 */
	public final static int FPS = 30;
	
	/**
	 * Enable/Disable FPS Log of the engine console
	 */
	public static boolean DEBUG_FPS = true;
	
	/**
	 * Enable/Disable FPS Log of the engine console
	 */
	public static boolean DEBUG_UPS = true;
	
	/**
	 * Enable/Disable FileUtils Log of the engine console
	 */
	public final static boolean DEBUG_FILE_UTILS = true;

	/**
	 * Enable/Disable Texture load Log of the engine console
	 */
	public final static boolean DEBUG_TEXTURE = true;
	
	/**
	 * Enable/Disable Model Loader Log of the engine console
	 */
	public final static boolean DEBUG_MODEL_LOADER = true;
	
	/**
	 * Enable/Disable Shaders Warnings Log of the engine console
	 */
	public final static boolean DEBUG_SHADER_WARNING = false;
	
	/**
	 * Number of Samples for MSAA in OpenGL
	 */
	public static final int MSAA_SAMPLES = 8;

	/**
	 * This is a Vector3f(1, 0, 0)
	 */
	public final static Vector3f X_AXIS = new Vector3f(1, 0, 0);

	/**
	 * This is a Vector3f(0, 1, 0)
	 */
	public final static Vector3f Y_AXIS = new Vector3f(0, 1, 0);
	
	/**
	 * This is a Vector3f(0, 0, 1)
	 */
	public final static Vector3f Z_AXIS = new Vector3f(0, 0, 1);

	/**
	 * Max number of DirectionalLight in one scene
	 */
	public final static int MAX_DIRECTIONAL_LIGHTS = 5;
	
	/**
	 * Max number of PointLight in one scene
	 */
	public final static int MAX_POINT_LIGHTS = 20;
	
	/**
	 * Max number of SpotLight in one scene
	 */
	public final static int MAX_SPOT_LIGHTS = 20;

	/**
	 * VSync enable / disable in the application
	 */
	public static final boolean VSync = false;
	
	/**
	 * Enable/Disable Wireframe render for the main shader
	 */
	public static boolean WIREFRAME_ENABLED = false;
	
	/**
	 * Enable/Disable lights for the main shader
	 */
	public static boolean LIGHTS_ENABLED = true;

}
