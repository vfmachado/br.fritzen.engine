package br.fritzen.engine.premodel;

import java.util.ArrayList;
import java.util.List;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyTypedEvent;
import br.fritzen.engine.platform.opengl.OpenGLShader;
import br.fritzen.engine.platform.opengl.OpenGLShaderType;
import br.fritzen.engine.premodels.Triangle;
import br.fritzen.engine.renderer.shader.Shader;
import br.fritzen.engine.utils.Pair;

/**
 * 
 * @author fritz
 *
 */
public class TriangleTest {

	public static void main(String[] args) {

		EngineLog.info("Starting app");

		Application app = Application.create("App Teste", 1280, 720);

		Layer myLayer = new Layer("Test Layer - Sandbox") {

			float[] positions = {
					  -1.0f, -1.0f, 0,  //0
		               0.0f,  1.0f, 0,	//1
		               1.0f, -1.0f, 0	//2
			};
			
			Triangle triangle = new Triangle(positions);
			float[] color = {1, 0, 0};
			Shader shader;
			
			@Override
			public void onAttach() {
				
				List<Pair<String, OpenGLShaderType>> shaders = new ArrayList<Pair<String, OpenGLShaderType>>();
				shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/vertex.shader", OpenGLShaderType.VERTEX));
				shaders.add(new Pair<String, OpenGLShaderType>("shaders/simple/fragment.shader", OpenGLShaderType.FRAGMENT));
				this.shader = new OpenGLShader(shaders);
				
			}
			
			
			@Override
			public void onUpdate(float deltatime) {
				// System.out.println("Mouse at: " + Input.getMousePos());
				this.triangle.setColor(color);
				//this.triangle.updateUniforms(shader);
			}
			
			@Override
			public void onImGuiRender() {

			}

			@Override
			public void onOvent(Event e) {

				if (e.getEventType() == EventType.KeyTypedEvent) {
					KeyTypedEvent evt = (KeyTypedEvent) e;
					System.out.println("" + ((char) evt.getKeyCode()));
				}

				if (e.getEventType() == EventType.MouseMovedEvent) {
					// System.out.println(Input.getMousePos());
				}

			}

			@Override
			public void onRender() {
				this.triangle.draw(shader);
			}
			
		};

		app.addLayer(myLayer);
		
		app.run();
	}

}
