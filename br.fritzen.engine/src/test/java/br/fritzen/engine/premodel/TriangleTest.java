package br.fritzen.engine.premodel;

import br.fritzen.engine.Application;
import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.core.layers.Layer;
import br.fritzen.engine.events.Event;
import br.fritzen.engine.events.EventType;
import br.fritzen.engine.events.key.KeyTypedEvent;
import br.fritzen.engine.premodels.Triangle;

/**
 * FOR NOW... THIS DON'T MAKE ANY SENSE T_T
 * @author fritz
 *
 */
public class TriangleTest {

	public static void main(String[] args) {

		EngineLog.info("Starting app");

		Application app = Application.create("App Teste", 1280, 720);

		Layer myLayer = new Layer("Test Layer - Sandbox") {

			float[] positions = {
					  -0.8f, -0.8f, 0,  //0
		               0.0f, -0.8f, 0,	//1
		              -0.8f,  0.0f, 0	//2
			};
			
			Triangle triangle = new Triangle(positions);
			
			
			@Override
			public void onUpdate() {
				// System.out.println("Mouse at: " + Input.getMousePos());
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

		};

		app.addLayer(myLayer);
		
		app.run();
	}

}
