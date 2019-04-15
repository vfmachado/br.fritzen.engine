package br.fritzen.engine.imgui;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import glm_.vec2.Vec2;
import imgui.Context;
import imgui.ImGui;
import imgui.impl.ImplGL3;
import imgui.impl.LwjglGlfw;
import imgui.impl.LwjglGlfw.GlfwClientApi;
import uno.glfw.GlfwWindow;

public class ImGuiLayer extends Layer {

	private ImplGL3 imguiGL3 = ImplGL3.INSTANCE;
    
	private LwjglGlfw imguiGlfw = LwjglGlfw.INSTANCE;
	
	private ImGui imgui = ImGui.INSTANCE;
	
	//private IO io;
	
	private Context ctx;
	
	private boolean showDemo[] = {true};
	
	
	public ImGuiLayer() {
		super("ImGuiLayer");
		
		imgui = ImGui.INSTANCE;
		
		
		
	}
	
	
	public void begin() {
		
		/*
		ImGui_ImplOpenGL3_NewFrame();
		ImGui_ImplGlfw_NewFrame();
		ImGui::NewFrame();
		 */

		imguiGlfw.newFrame();
	
		imgui.showDemoWindow(showDemo);
		
	}
	
	
	public void end() {
		
		
		imgui.render();
		imguiGL3.renderDrawData(imgui.getDrawData());
		
	}
	
	
	
	@Override
	public void onAttach() {
		
		ctx = new Context(null);
		
		//io = imgui.getIo();
		
		imguiGlfw.init(GlfwWindow.from(Application.getWindow().getNativeWindow()), true, GlfwClientApi.OpenGL);
		
	}

	
	@Override
	public void onDetach() {
		/*
		ImGui_ImplOpenGL3_Shutdown();
		ImGui_ImplGlfw_Shutdown();
		ImGui::DestroyContext();
		*/
		this.imguiGL3.destroyDeviceObjects();
		this.ctx.shutdown();
	}

	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onOvent(Event e) {
		
	}

	
	
	@Override
	public void onImGuiRender() {
		
		if (imgui.button("TESTE", new Vec2(100, 100))) {
			System.out.println("OKKKKK");
		}
		
	}
	
	
	public void updateWindowReference() {
		ctx = new Context(null);
		imguiGlfw.init(GlfwWindow.from(Application.getWindow().getNativeWindow()), true, GlfwClientApi.OpenGL);
	}

}
