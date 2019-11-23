package br.fritzen.engine.core;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyEvent;
import br.fritzen.engine.events.key.KeyPressedEvent;
import br.fritzen.engine.imgui.GUI;
import br.fritzen.engine.window.Window.WindowMode;
import imgui.ImGui;


public class WindowModeTest {

	public static void main(String[] args) {

		Application app = Application.create("App Window Mode Teste", 800, 600);
		
		app.addLayer(new Layer("GUI Layer") {
			
			@Override
			public void onImGuiRender() {
				ImGui gui = ImGui.INSTANCE;
				
				gui.begin("INFO", GUI.TRUE, 0);
				gui.text("F10 - Window Mode"); 
				gui.text("F11 - Borderless Mode");
				gui.text("F12 - FullScreen Mode");
				gui.end();
				
			}
			
			@Override
			public void onOvent(Event e) {
				
				if (e.getEventType() == EventType.KeyPressedEvent) {
					
					KeyPressedEvent evt = (KeyPressedEvent)e;
					
					if (evt.getKeyCode() == KeyEvent.KEY_F12) {
						
						Application.getWindow().setWindowMode(WindowMode.FULL_SCREEN);

					
					} else if (evt.getKeyCode() == KeyEvent.KEY_F11) {
						
						Application.getWindow().setWindowMode(WindowMode.BORDERLESS);

					
					} else if (evt.getKeyCode() == KeyEvent.KEY_F10) {
					
						Application.getWindow().setWindowMode(WindowMode.WINDOWED);

					
					} 
				}
				
				if (e.getEventType() == EventType.WindowResizeEvent) {
					System.out.println("Windows Resized");
				}
			}
			
		});
		
		app.run();

	}

}
