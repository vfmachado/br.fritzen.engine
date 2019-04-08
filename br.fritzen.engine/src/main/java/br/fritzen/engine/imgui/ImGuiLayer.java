package br.fritzen.engine.imgui;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import imgui.Context;
import imgui.IO;
import imgui.ImGui;
import imgui.impl.ImplGL3;
import imgui.impl.LwjglGlfw;
import imgui.impl.LwjglGlfw.GlfwClientApi;
import uno.glfw.GlfwWindow;

public class ImGuiLayer extends Layer {

	private ImplGL3 imguiGL3 = ImplGL3.INSTANCE;
    
	private LwjglGlfw imguiGlfw = LwjglGlfw.INSTANCE;
	
	private ImGui imgui = ImGui.INSTANCE;
	
	private IO io;
	
	private Context ctx;
	
	
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
		
		//ImGui_ImplOpenGL3_NewFrame();
		imguiGlfw.newFrame();
		imgui.newFrame();
		
		
	}
	
	
	public void end() {
		
		
		imgui.render();
		imguiGL3.renderDrawData(imgui.getDrawData());
		
	}
	
	
	
	@Override
	public void onAttach() {
		
		ctx = new Context();
		io = imgui.getIo();
		imguiGlfw.init(GlfwWindow.from(Application.getInstance().getWindow().getNativeWindow()), true, GlfwClientApi.OpenGL);
		
	}

	
	@Override
	public void onDetach() {
		/*
		ImGui_ImplOpenGL3_Shutdown();
		ImGui_ImplGlfw_Shutdown();
		ImGui::DestroyContext();
		*/
		
		
	}

	
	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onOvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onImGuiRender() {
		/*
		static bool show = true;
		ImGui::ShowDemoWindow(&show);		
		 */
		
		imgui.showDemoWindow(showDemo);
	}

	
	private static boolean showDemo[] = {true};
	
}
