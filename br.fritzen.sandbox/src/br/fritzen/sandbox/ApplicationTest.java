package br.fritzen.sandbox;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import imgui.ImGui;

public class ApplicationTest {

	public static void main(String[] args) {
		
		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);
		
		app.addLayer(new Layer("Test Layer - Sandbox") {
			
			@Override
			public void onUpdate() {
				System.out.println("Mouse at: " + Input.getMousePos());
			}
			
			boolean open[] = {true};
			@Override
			public void onImGuiRender() {
				ImGui imgui = ImGui.INSTANCE;
				imgui.begin("Teste", open, 0);
				imgui.text("Exemplo in App");
				imgui.end();
				
			}
		
		});
		
		app.run();
	}
	
}
