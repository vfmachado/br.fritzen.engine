package br.fritzen.sandbox;

import java.io.IOException;
import java.util.logging.FileHandler;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyTypedEvent;
import br.fritzen.engine.imgui.GUI;
import br.fritzen.engine.platform.opengl.Texture2DGL;
import br.fritzen.engine.renderer.Texture;
import br.fritzen.sandbox.gui.ImageButton;
import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.ImGui;
import imgui.TabBarFlag;
import imgui.TabItemFlag;
import imgui.WindowFlag;

public class ApplicationTest {
	
	public static void setLookAndFeel() {
		
		try {
            UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
	    
	    }
		
	}
	
	public static void main(String[] args) {
		
		setLookAndFeel();
		
		try {
			FileHandler fh = new FileHandler("log.info");
			EngineLog.setOutStream(fh);
			//EngineLog.setSeverity(EngineLog.LogLevel.WARNING);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
			
		
		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);
			

		
		app.addLayer(new Layer("GUI Layer") {
			
			Texture albedoTexture = new Texture2DGL("dog.jpg");
			boolean [] useTexture = {true};
			
			float albedoColor[] = {0.0f, 0.0f, 0.0f};
			boolean [] useColor = {true};
			boolean isUseColor = false;
			boolean [] colorPickerWindow = {false};
			boolean colorSelecting = false;
			
			ImageButton albedoTextureButton = new ImageButton(albedoTexture);
			
			@Override
			public void onImGuiRender() {
				
				//TODO - create a beauty window :P
				ImGui gui = ImGui.INSTANCE;
				
				gui.beginMainMenuBar();
				if ( gui.menuItem("File", "", false, true) ) {
					
										
				}
				gui.endMainMenuBar();
				
				
				int windowFlag =  WindowFlag.NoDecoration.i;
				gui.begin("#SETTINGS", GUI.TRUE, 0);
				
				if (gui.beginTabBar("##TABS", TabBarFlag.None.i) ) {
				
					int tabItemFlags = TabItemFlag.NoCloseButton.i | TabItemFlag.NoCloseWithMiddleMouseButton.i | TabItemFlag.NoPushId.i;
					
					if (gui.beginTabItem("Settings", GUI.TRUE, 0,  tabItemFlags)) {
						gui.text("GENERAL SETTINGS GUI TEST");
						gui.endTabItem();
					}
					
					if (gui.beginTabItem("World", GUI.TRUE, 0, tabItemFlags)) {
						gui.text("WORLD SETTINGS GUI TEST");
						gui.endTabItem();
					}
					
					if (gui.beginTabItem("Model loader", GUI.TRUE, 0, tabItemFlags ) ) {
						
						gui.text("LOADER MODEL GUI TEST");
						
						gui.separator();
						gui.text("TEXTURE Information");
						
						if (gui.collapsingHeader("Albedo", 0)) {
							
							gui.text("texto para o item 01");
							
							albedoTextureButton.imageButton(100, 100);
							
							if (gui.checkbox("Use", useTexture) ) {
								
							}
							
							if (gui.colorButton("Color", new Vec4(), GUI.NONE_FLAG, new Vec2(100, 100)) || colorPickerWindow[0]) {
								
								colorPickerWindow[0] = true;
								
								if (gui.begin("Color pickup", colorPickerWindow, GUI.NONE_FLAG)) {
									gui.colorPicker3("Color", albedoColor, GUI.NONE_FLAG);
									gui.end();
								}
							}
							
							/*
							//gui.colorEdit3("Color", albedoColor, GUI.NONE_FLAG);							
							if (gui.checkbox("Color", useColor)) {
								isUseColor = !isUseColor;
							//	imgui.colorPickerOptionsPopup(albedoColor, GUI.NONE_FLAG);
							//	imgui.colorPicker3("Color", albedoColor, GUI.NONE_FLAG);
							}
							*/
						}
						
						if (gui.collapsingHeader("TESTE 02", 0)) {
							gui.text("texto diferente para 02");
						}
						
						
						gui.endTabItem();
					}
					
					gui.endTabBar();
				}
				
				gui.end();
				
				
			}
			
		});
		
		app.addLayer(new Layer("Test Layer - Sandbox") {
			
			@Override
			public void onUpdate(float deltatime) {
				//System.out.println("Mouse at: " + Input.getMousePos());
			}
			
			
			@Override
			public void onImGuiRender() {
				
				ImGui imgui = ImGui.INSTANCE;
				imgui.begin("App Teste", GUI.TRUE, 0);
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
