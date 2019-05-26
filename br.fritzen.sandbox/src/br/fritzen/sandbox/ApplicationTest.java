package br.fritzen.sandbox;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.input.Input;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyTypedEvent;
import imgui.ImGui;

public class ApplicationTest {
	
	public static void main(String[] args) {
		
		boolean mtrue[] = {true};
		
		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);
		
		app.addLayer(new Layer("GUI Layer") {
			
			@Override
			public void onImGuiRender() {
				
				//TODO - create a beauty window :P
				ImGui imgui = ImGui.INSTANCE;
				
				imgui.beginMainMenuBar();
				if ( imgui.menuItem("File", "", false, true) ) {
					
										
				}
				imgui.endMainMenuBar();
				
				imgui.begin("MAIN GUI", mtrue, 1);
				imgui.beginTabBar("Settings", 0);
				imgui.beginTabItem("Teste", mtrue, 0, 0);
				imgui.text("GUI LAYER TEST");
				
				imgui.endTabItem();
				imgui.endTabBar();
				imgui.end();
				
				
			}
			
		});
		
		app.addLayer(new Layer("Test Layer - Sandbox") {
			
			@Override
			public void onUpdate() {
				//System.out.println("Mouse at: " + Input.getMousePos());
			}
			
			
			@Override
			public void onImGuiRender() {
				
				ImGui imgui = ImGui.INSTANCE;
				imgui.begin("App Teste", mtrue, 0);
				imgui.text("Exemplo in App");
				imgui.text("Small Layer created by user");
				imgui.end();
				
			}
			
			@Override
			public void onOvent(Event e) {
				
				if (e.getEventType() == EventType.KeyTypedEvent) {
					KeyTypedEvent evt = (KeyTypedEvent) e;
					System.out.println("" + ((char)evt.getKeyCode()));
				}
				
				if (e.getEventType() == EventType.MouseMovedEvent) {
					//System.out.println(Input.getMousePos());
				}
				
			}
		
		});
		
		app.run();
	}
	
}
