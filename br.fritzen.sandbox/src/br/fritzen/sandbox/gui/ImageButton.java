package br.fritzen.sandbox.gui;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.fritzen.engine.platform.opengl.Texture2DGL;
import br.fritzen.engine.renderer.Texture;
import glm_.vec2.Vec2;
import glm_.vec4.Vec4;
import imgui.ImGui;

public class ImageButton  {
	
	Texture texture;
	ImGui gui =  ImGui.INSTANCE;
	
	public ImageButton() {
	
	}
	
	public ImageButton(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public void imageButton(int sizeX, int sizeY) {
	
		if ( gui.imageButton(texture.getRendererId(), new Vec2(sizeX, sizeY), new Vec2(1, 1), new Vec2(0, 0), 0, new Vec4(1, 1, 1, 1), new Vec4(1, 1, 1, 1)) ) {
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG & GIF Images", "jpg", "gif", "tga");
		    chooser.setFileFilter(filter);
		    chooser.setVisible(true);
		    chooser.setRequestFocusEnabled(true);
		    int returnVal = chooser.showOpenDialog(null);
		    
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		       String path = 
		            chooser.getSelectedFile().getAbsolutePath();

		       texture = new Texture2DGL(path);
		    }
			
		}
	}
}
