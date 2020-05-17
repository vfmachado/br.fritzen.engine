package br.fritzen.engine;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.MainLoopFixedFPS;
import br.fritzen.engine.core.MainLoopUnlimited;
import br.fritzen.engine.core.OSDetection;
import br.fritzen.engine.core.OSDetection.OSType;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.core.layers.LayerStack;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventDispatcher;
import br.fritzen.engine.events.window.WindowCloseEvent;
import br.fritzen.engine.platform.windows.WindowsWindowImpl;
import br.fritzen.engine.renderer.RenderCommand;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.window.Window;


public class Application extends MainLoopUnlimited {

	private static Application instance = null;
	
	private Window window;
	
	private EventDispatcher dispatcher;
	
	private LayerStack layerStack;
	
	//private ImGuiLayer imguiLayer;

			
	
	private Application(String title, int width, int height) {

		this.dispatcher = new EventDispatcher();
		this.layerStack = new LayerStack();

	}
	
	
	public static Application create(String title, int width, int height) {
		
		EngineLog.info("Creating applicaiton instance");
		
		instance = new Application(title, width, height);
		
		if (OSDetection.getOS() == OSType.Windows)
			instance.window = new WindowsWindowImpl(width, height, title);

		Renderer.init();
		
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
	
	
	@Override
	protected void init() {
		
		//imguiLayer = new ImGuiLayer();
		//layerStack.pushOverlay(imguiLayer);
		
	}

	
	@Override
	protected void input() {
		
	}
	
	
	public void onEvent(Event e) {
		
		dispatcher.setEvent(e);
		
		dispatcher.dispatch(this::onWindowCloseEvent, WindowCloseEvent.class);
		
		for (Layer layer : layerStack) {
			layer.onEvent(e);
		}
		
	}
	
	
	@Override
	protected void render() {
		
		RenderCommand.setViewPort(window.getWidth(), window.getHeight());
		
		RenderCommand.clear();
		
		for (Layer layer : layerStack) {
			layer.onRender();
		}
		
		
		/*
		imguiLayer.begin();
		
		
		for (Layer layer : layerStack) {
			layer.onImGuiRender();
		}
		
		imguiLayer.end();
		*/
		/*
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
	
	
	private float count = 0, medianSum, median;
	
	@Override
	protected void update(float deltatime) {
		
		if (count >= 60) {
			
			median = medianSum/60;
			
			EngineLog.info("\tFPS: " + 1f/median + "\t" + median*1000 + " ms" );
			
			count = 0;
			medianSum = 0;
			
		}
		
		count++;
		medianSum += deltatime;
		
		
		for (Layer layer : layerStack) {
		
			layer.onUpdate(deltatime);
		
		}
		
	
	}
	
	
	private boolean onWindowCloseEvent(Event e) {
	
		this.stop();
		return true;
		
	}

}
