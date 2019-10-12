package br.fritzen.engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoopUnlimited;
import br.fritzen.engine.core.OSDetection;
import br.fritzen.engine.core.OSDetection.OSType;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.core.layers.LayerStack;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.gameobject.GameObject;
import br.fritzen.engine.imgui.GUI;
import br.fritzen.engine.imgui.ImGuiLayer;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;
import br.fritzen.engine.renderer.GraphicsContext;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
import br.fritzen.engine.window.Window;
import imgui.ImGui;

//TODO - CREATE A MORE GENERIC Application
public class Application extends MainLoopUnlimited {

	private static Application instance = null;
	
	private Window window;
	
	private GraphicsContext graphicsContext;
	
	private EventDispatcher dispatcher;
	
	private LayerStack layerStack;
	
	private ImGuiLayer imguiLayer;
	
	public List<GameObject> scene = new ArrayList<GameObject>();
	
		
	
	private Application(String title, int width, int height) {

		this.dispatcher = new EventDispatcher();
		this.layerStack = new LayerStack();
		
	}
	
	
	public static Application create(String title, int width, int height) {
		
		EngineLog.info("Creating applicaiton instance");
		
		instance = new Application(title, width, height);
		
		if (OSDetection.getOS() == OSType.Windows)
			instance.window = new WindowsWindowImpl(width, height, title);
					
		return instance;
		
	}
	
	
	public static Application getInstance() {
		
		if (instance == null) {
			instance = Application.create("Fritzen Engine", 800, 600);
		}
		
		return instance;
	}
	
	
	public static Window getWindow() {
		return instance.window;
	}
	
	
	public void addLayer(Layer layer) {
		this.layerStack.pushLayer(layer);
	}
	
	
	Shader shader;
	VertexArray vao;
	IndexBuffer ibo;
	String systemExpecs;
	
	@Override
	protected void init() {
		
		this.graphicsContext = this.window.getContext();
		
		imguiLayer = new ImGuiLayer();
		layerStack.pushOverlay(imguiLayer);
		
		/*
		List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/vertex.shader", OpenGLShaderType.VERTEX));
		shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/fragment.shader", OpenGLShaderType.FRAGMENT));
		this.shader = new OpenGLShader(shaders);
		
		
		float[] positions = {
				  -0.8f, -0.8f, 0,  //0
	               0.0f, -0.8f, 0,	//1
	              -0.8f,  0.0f, 0,	//2
	               0.0f,  0.0f, 0,	//3
	               0.8f,  0.8f, 0,	//0
	               0.0f,  0.8f, 0,	//1
	               0.8f,  0.0f, 0,	//2
	               0.0f,  0.0f, 0, 	//3
	            };
		
		//VertexBufferObject vbo = new VertexBufferObject(EngineBuffers.createFloatBuffer(positions));
		VertexBuffer vbo = VertexBuffer.create(EngineBuffers.createFloatBuffer(positions), positions.length * 4);
		
		
		this.vao = VertexArray.create();
		//this.vao = new OpenGLVertexArray();
		//this.vao.addInterleavedVBO(vbo, layouts);
			
		this.vao.addVB(vbo, 0, 3);
		
		int[] indices = { 0, 1, 2, 1, 3, 2,
						  4, 5, 6, 5, 7, 6};
		
		ibo = IndexBuffer.create(EngineBuffers.createIntBuffer(indices), indices.length);
		this.vao.setIB(ibo);
		//ibo.unbind();
		*/
		Renderer.get().clearColor(0, 1.0f, 1.0f, 1.0f);
		
		
		systemExpecs = "Vendor: " + graphicsContext.getVendor()
		 + "\nRenderer: " + graphicsContext.getRenderer()
		 + "\nVersion: " + graphicsContext.getVersion();
	}

	
	@Override
	protected void input() {
		
		/*
		if (Input.isKeyPressed(Input.KEY_A)) {
			System.out.println("OK");
		}
		*/
		//System.out.println("Mouse at: " + Input.getMousePos());
	}
	
	
	public void onEvent(Event e) {
		
		dispatcher.setEvent(e);
		
		dispatcher.dispatch(this::onWindowCloseEvent, WindowCloseEvent.class);
		
		//dispatcher.dispatch(this::onFKeyWindowModeEvent, KeyPressedEvent.class);
		
		
		for (Layer layer : layerStack) {
			layer.onOvent(e);
		}
		
	}
	
	
	//TODO - REMOVE THE CODE FROM RENDER - ANY APP IS RUNNING IT =/
	@Override
	protected void render() {
		
		/*
		GL11.glClearColor(0, 0.7f, 0.7f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		GL11.glViewport(0, 0, getWindow().getWidth(), getWindow().getHeight());
		
		
		this.shader.bind();
		this.shader.updateUniform(ShaderUniform.color, 1.0f, 0.2f, 0.2f);
		
		this.vao.bind();
		this.ibo.bind();
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ibo.getCount(), GL11.GL_UNSIGNED_INT, ibo.getOffset());
		
		//scene draw
		for (GameObject go :  scene) {
			go.draw();
		}
		*/
		
		Renderer renderer = Renderer.get();
		renderer.clear();
		
		/*
		this.shader.bind();
		this.shader.updateUniform(ShaderUniform.color, 1.0f, 0.2f, 0.2f);
		
		renderer.draw(this.vao, shader);
		*/
		
		for (Layer layer : layerStack) {
			layer.onRender();
		}
		
		/*
		imguiLayer.begin();
		
		
		for (Layer layer : layerStack) {
			layer.onImGuiRender();
		}
		
		
		ImGui imgui = ImGui.INSTANCE;

		imgui.begin("Renderer Info", GUI.TRUE, GUI.NONE_FLAG);
		/*
		imgui.text("Vendor: " + graphicsContext.getVendor());
		imgui.text("Renderer: " + graphicsContext.getRenderer());
		imgui.text("Version: " + graphicsContext.getVersion());

		imgui.text("FPS: " + 1000f/loopTime + "\t" + loopTime + " ms" );
		imgui.text("Median Values:");
		
		imgui.text("FPS: " + 1000f/median + "\t" + median + " ms" );
		imgui.end();
		
		imguiLayer.end();
		*/
		//the update method from window is related to render (VSYNC) or update ??
		getWindow().onUpdate();
	}
	
	private float loopTime;
	
	float count = 0, medianSum, median;
	
	@Override
	protected void update(float deltatime) {
		
		//EngineLog.info("Deltatime (ms): " + deltatime);
		
		/*
		if (count >= 1000) {
			
			median = medianSum/1000;
			count = 0;
			medianSum = 0;
			
		}
		
		count++;
		medianSum += deltatime;
		*/
		
		for (GameObject go :  scene) {
			go.updateUniforms(this.shader);
		}
		
		for (Layer layer : layerStack) {
		
			layer.onUpdate(deltatime);
		
		}
		
	
	}
	
	
	private boolean onWindowCloseEvent(Event e) {
	
		this.stop();
		return true;
		
	}
	
	
	/*
	//BEING USED BY DISPATCHER
	private boolean onFKeyWindowModeEvent(Event e) {
		
		KeyPressedEvent evt = (KeyPressedEvent)e;
		
		if (evt.getKeyCode() == KeyEvent.KEY_F12) {
			
			getWindow().setWindowMode(WindowMode.FULL_SCREEN);

		
		} else if (evt.getKeyCode() == KeyEvent.KEY_F11) {
			
			getWindow().setWindowMode(WindowMode.BORDERLESS);

		
		} else if (evt.getKeyCode() == KeyEvent.KEY_F10) {
		
			getWindow().setWindowMode(WindowMode.WINDOWED);

		
		} 
		
		return false;
	}
	*/
}
