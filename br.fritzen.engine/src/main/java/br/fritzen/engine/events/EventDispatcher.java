package br.fritzen.engine.events;

public class EventDispatcher {

	private Event event;
	
	
	public EventDispatcher(Event e) {
		this.event = e;
	}

		
	@SuppressWarnings("rawtypes")
	public void dispatch(Dispatcher disp, Class type) {
	
		//TODO CHECK THE OPTION TO THE ACTUAL EVENT DON'T BE HANDLED BY NEXT DISPATCHS.
		if (event.isHandled())
			return;
		
		if (event.getEventType().name().equals(type.getSimpleName())) {
			event.setHandled(disp.dispatch(event));
		}
		
	}
	
}
