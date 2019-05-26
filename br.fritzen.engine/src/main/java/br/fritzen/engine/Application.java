package br.fritzen.engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoop;
import br.fritzen.engine.core.OSDetection;
import br.fritzen.engine.core.OSDetection.OSType;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.core.layers.LayerStack;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.imgui.ImGuiLayer;
import br.fritzen.engine.platform.opengl.IndexBufferObject;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.platform.opengl.VertexArrayObject;
import br.fritzen.engine.platform.opengl.VertexBufferLayout;
import br.fritzen.engine.platform.opengl.VertexBufferObject;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.renderer.shader.ShaderUniform;
import br.fritzen.engine.utils.EngineBuffers;
import br.fritzen.engine.utils.Pair;
import br.fritzen.engine.window.Window;
import br.fritzen.engine.window.Window.WindowMode;
import imgui.ImGui;

public class Application extends MainLoop {

	private static Application instance = null;
	
	private Window window;
	
	private EventDispatcher dispatcher;
	
	private LayerStack layerStack;
	
	private ImGuiLayer imguiLayer;
	
	
	private Application() {
		//this("Fritzen Engine", 1280, 720);
	}
	
	
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
	VertexArrayObject vao;
	IndexBufferObject ibo;
	
	@Override
	protected void init() {
		
		imguiLayer = new ImGuiLayer();
		layerStack.pushOverlay(imguiLayer);
		
		
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
		
		VertexBufferObject vbo = new VertexBufferObject(EngineBuffers.createFloatBuffer(positions));
		
		
		this.vao = new VertexArrayObject();
		//this.vao.addInterleavedVBO(vbo, layouts);
			
		this.vao.addVBO(vbo, 0, 3);
		
		int[] indices = { 0, 1, 2, 1, 3, 2,
						  4, 5, 6, 5, 7, 6};
		
		ibo = new IndexBufferObject(EngineBuffers.createIntBuffer(indices));
		ibo.unbind();
		
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
		
		//EngineLog.info(e.toString());
		
		dispatcher.setEvent(e);
		
		dispatcher.dispatch(this::onWindowCloseEvent, WindowCloseEvent.class);
		
		dispatcher.dispatch(this::onFKeyWindowModeEvent, KeyPressedEvent.class);
		
		
		for (Layer layer : layerStack) {
			layer.onOvent(e);
		}
		
	}
	
	
	@Override
	protected void render() {
		
		
		GL11.glClearColor(0, 0.7f, 0.7f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		
		GL11.glViewport(0, 0, getWindow().getWidth(), getWindow().getHeight());
		
		//System.out.println("GL Viewport: " + getWindow().getWidth() + "x" + getWindow().getHeight());
		/*
		GL11.glColor3f(1, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2f( -1 , -1);
		GL11.glVertex2f(  0 ,  1);
		GL11.glVertex2f(  1 , -1 );
		GL11.glEnd();
		*/
		
		this.shader.bind();
		this.shader.updateUniform(ShaderUniform.color, 1.0f, 0.2f, 0.2f);
		
		this.vao.bind();
		this.ibo.bind();
		
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, ibo.getCount(), GL11.GL_UNSIGNED_INT, ibo.getOffset());
		
		
		imguiLayer.begin();
		
		for (Layer layer : layerStack) {
			layer.onImGuiRender();
		//	System.out.println("Render IMGUI: " + layer.getName());
		}
		
		imguiLayer.end();
		
		//the update method from window is related to render (VSYNC) or update ??
		getWindow().onUpdate();
	}
	
	public boolean[] open=  {true};
	
	
	@Override
	protected void update(long deltatime) {
		
		
		
		for (Layer layer : layerStack) {
		
			layer.onUpdate();
		
		}
		
	
	}
	
	
	private boolean onWindowCloseEvent(Event e) {
	
		this.stop();
		return true;
		
	}
	
	
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
}
