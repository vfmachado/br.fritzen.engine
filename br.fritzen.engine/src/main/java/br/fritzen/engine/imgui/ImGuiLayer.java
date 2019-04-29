package br.fritzen.engine.imgui;

import java.util.Arrays;

import org.lwjgl.glfw.GLFW;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventCategory;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.events.key.KeyReleasedEvent;
import glm_.vec2.Vec2;
import imgui.ConfigFlag;
import imgui.Context;
import imgui.IO;
import imgui.ImGui;
import imgui.impl.LwjglGlfw;
import imgui.impl.LwjglGlfw.GlfwClientApi;
import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import uno.glfw.GlfwWindow;
import uno.glfw.Key;


public class ImGuiLayer extends Layer {

	//private ImplGL3 imguiGL3 = new ImplGL3();
    
	private LwjglGlfw lwjglGlfw;// = new LwjglGlfw();
	
	private GlfwWindow window;
    private uno.glfw.glfw glfw = uno.glfw.glfw.INSTANCE;
    
	
	private ImGui imgui = ImGui.INSTANCE;
	
	private IO io;
	
	private Context ctx;
	
	private boolean showDemo[] = {true};
	
	
	public ImGuiLayer() {
		super("ImGuiLayer");
		
		//window.getNCharCallback().
		
		window = GlfwWindow.from(Application.getWindow().getNativeWindow());
		//window.installDefaultCallbacks();
		window.installNativeCallbacks(); //this line install the callbacks for imgui work but disable my glfw callback
		
	/*
		window.setKeyCallback( new Function4<Integer, Integer, Integer, Integer, Unit>() {

			@Override
			public Unit invoke(Integer key, Integer scancode, Integer action, Integer mods) {
				// TODO Auto-generated method stubkey, scancode, action, mods
				Event event;
				switch (action) {
					
					case GLFW.GLFW_PRESS:
						event = new KeyPressedEvent(key, 0);
						Application.getInstance().onEvent(event);
						break;
					
					case GLFW.GLFW_RELEASE:
						event = new KeyReleasedEvent(key);
						Application.getInstance().onEvent(event);
						break;
					
					case GLFW.GLFW_REPEAT:
						event = new KeyPressedEvent(key, 1);
						Application.getInstance().onEvent(event);
						break;
					
				}
				return null;
			}
			
		});
		*/
		
		ctx = new Context(null);
		
		lwjglGlfw = new LwjglGlfw(window, true, GlfwClientApi.OpenGL, null);
		
		
		
		io = imgui.getIo();
		
		io.setWantCaptureKeyboard(false);
		System.out.println("IMGUI GLFW - Initialized");
	}
	
	
	public void begin() {
		
		/*
		ImGui_ImplOpenGL3_NewFrame();
		ImGui_ImplGlfw_NewFrame();
		ImGui::NewFrame();
		 */
		
		lwjglGlfw.newFrame();
		imgui.newFrame();		
		
		imgui.showDemoWindow(showDemo);
		
	}
	
	
	public void end() {
		
	
		 imgui.render();
	     lwjglGlfw.renderDrawData(imgui.getDrawData());
	     
	     
	}
	
	
	
	@Override
	public void onAttach() {
		
		
	}

	
	@Override
	public void onDetach() {
		/*
		ImGui_ImplOpenGL3_Shutdown();
		ImGui_ImplGlfw_Shutdown();
		ImGui::DestroyContext();
		*/
		//this.imguiGL3.destroyDeviceObjects();
		this.ctx.shutdown();
	}

	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onOvent(Event e) {
		/*
		if (e.getEventCategory() == EventCategory.Keyboard) {
			
			KeyEvent ke = (KeyEvent) e;
			if (e.getEventType() == EventType.KeyPressedEvent) {
				io.getKeysDown()[ke.getKeyCode()] = true;
			} else if (e.getEventType() == EventType.KeyReleasedEvent) {
				
				io.getKeysDown()[ke.getKeyCode()] = false;
				
				//TODO - WORK HERE
				io.addInputCharacter((char)ke.getKeyCode());
			} 
		}
		/*
		if (e.getEventCategory() == EventCategory.Keyboard) {
			
			KeyEvent ke = (KeyEvent) e;
			
			Key key = Key.Companion.of(ke.getKeyCode());
			//System.out.println(key);
			if (e.getEventType() == EventType.KeyPressedEvent) {
				window.onKeyPressed(key, 0);
			} else if (e.getEventType() == EventType.KeyReleasedEvent) {
				window.onKeyReleased(key, 0);
			} 
			
			//window.getDefaultKeyCallback().invoke(ke.getKeyCode(), 0, action, 0);
			
		}
		*/
	}

	
	float[] values = {0, 0, 0};
	@Override
	public void onImGuiRender() {
		
		if (imgui.button("TESTE", new Vec2(100, 100))) {
			System.out.println("OKKKKK");
		}
		
		
		if (imgui.dragFloat3("Teste float3", values, 0.1f, 0f, 10f, "%.1f", 1f)) {
			//EngineLog.info("Changing slide...");
		}
		
	}
	
}
