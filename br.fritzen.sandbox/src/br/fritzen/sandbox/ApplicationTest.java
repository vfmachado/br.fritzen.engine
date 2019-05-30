package br.fritzen.sandbox;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyTypedEvent;
import br.fritzen.engine.imgui.GUI;
import br.fritzen.engine.imgui.ImageButton;
import br.fritzen.engine.platform.opengl.Texture2DGL;
import br.fritzen.engine.renderer.Texture;
import imgui.ImGui;
import imgui.TabBarFlag;
import imgui.TabItemFlag;
import imgui.WindowFlag;

public class ApplicationTest {
	
	public static void setLookAndFeel() {
		try {
        // Set System L&F
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
	}
	
	public static void main(String[] args) {
		
		setLookAndFeel();
		
		boolean textureAlbedo[] = {true};
		
		float albedoColor[] = {0.0f, 0.0f, 0.0f};
		
		EngineLog.info("Starting app");
		
		Application app = Application.create("App Teste", 1280, 720);
		
		

		
		app.addLayer(new Layer("GUI Layer") {
			
			Texture albedoTexture = new Texture2DGL("src/dog.jpg");
			ImageButton albedoTextureButton = new ImageButton(albedoTexture);
			
			
			@Override
			public void onImGuiRender() {
				
				//TODO - create a beauty window :P
				ImGui imgui = ImGui.INSTANCE;
				
				imgui.beginMainMenuBar();
				if ( imgui.menuItem("File", "", false, true) ) {
					
										
				}
				imgui.endMainMenuBar();
				
				
				int windowFlag =  WindowFlag.NoDecoration.i;
				imgui.begin("#SETTINGS", GUI.TRUE, 0);
				
				if (imgui.beginTabBar("##TABS", TabBarFlag.None.i) ) {
				
					int tabItemFlags = TabItemFlag.NoCloseButton.i | TabItemFlag.NoCloseWithMiddleMouseButton.i | TabItemFlag.NoPushId.i;
					
					if (imgui.beginTabItem("Settings", GUI.TRUE, 0,  tabItemFlags)) {
						imgui.text("GENERAL SETTINGS GUI TEST");
						imgui.endTabItem();
					}
					
					if (imgui.beginTabItem("World", GUI.TRUE, 0, tabItemFlags)) {
						imgui.text("WORLD SETTINGS GUI TEST");
						imgui.endTabItem();
					}
					
					if (imgui.beginTabItem("Model loader", GUI.TRUE, 0, tabItemFlags ) ) {
						
						imgui.text("LOADER MODEL GUI TEST");
						
						imgui.separator();
						imgui.text("TEXTURE Information");
						
						if (imgui.collapsingHeader("Albedo", 0)) {
							//imgui.text("texto para o item 01");
							
							albedoTextureButton.imageButton(100, 100);
							
							/*
							if (imgui.imageButton(albedoTexture.getRendererId(), new Vec2(100, 100), new Vec2(1, 1), new Vec2(0, 0), 0, new Vec4(1, 1, 1, 1), new Vec4(1, 1, 1, 1)) ) {
								
								JFileChooser chooser = new JFileChooser();
							    FileNameExtensionFilter filter = new FileNameExtensionFilter(
							        "JPG & GIF Images", "jpg", "gif");
							    chooser.setFileFilter(filter);
							    int returnVal = chooser.showOpenDialog(null);
							    if(returnVal == JFileChooser.APPROVE_OPTION) {
							       String path = 
							            chooser.getSelectedFile().getAbsolutePath();

									albedoTexture = new Texture2DGL(path);
							    }
								
								
							}
							*/
							imgui.checkbox("Use", textureAlbedo);
							//imgui.
							/*
							if (imgui.checkbox("Color", useColor)) {
								imgui.colorPickerOptionsPopup(albedoColor, GUI.NONE_FLAG);
								imgui.colorPicker3("Color", albedoColor, GUI.NONE_FLAG);
							}
							 */
						}
						
						if (imgui.collapsingHeader("TESTE 02", 0)) {
							imgui.text("texto diferente para 02");
						}
						
						
						imgui.endTabItem();
					}
					
					imgui.endTabBar();
				}
				
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
				imgui.begin("App Teste", GUI.TRUE, 0);
				imgui.text("Exemplo in App");
				imgui.text("Small Layer created by user");
				if (imgui.checkbox("TESTE", GUI.FALSE)) {
					imgui.text("FOI MARCADO");
				}
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
