package br.fritzen.engine.core;

public abstract class MainLoopFixedFPS extends MainLoop {

private final static long SECOND_IN_NANOS = 1_000_000_000L;
	
	private final static long DESIRED_UPDATE_TIME = SECOND_IN_NANOS/EngineState.UPS;
	
	private final static long DESIRED_FPS_TIME = SECOND_IN_NANOS/EngineState.FPS;
	
	private boolean isRunning = false;
	
	private long overSleep = 0;
	
	
	public void run() {

		EngineLog.info("Initializing systems");
		this.init();
		
		isRunning = true;
		
		int currentUPS = 0;
		int currentFPS = 0;
		
		long timer = System.nanoTime();
		
		long updateTime = 0;
		long renderTime = 0;
		
		long beforeTime = timer;
		long afterTime = timer;
			
		long lastRender =0;
		
		EngineLog.info("Application main loop");
		while (isRunning) {
			
			long deltaTime = afterTime - beforeTime; 
				
			if (deltaTime >= DESIRED_UPDATE_TIME) {
				
				long initialTime = System.nanoTime();
				
				this.update(deltaTime/1_000_000);
				this.input();
				currentUPS++;
				
				updateTime = System.nanoTime() - initialTime;
				beforeTime = System.nanoTime() - (deltaTime - DESIRED_UPDATE_TIME + updateTime);
			
			} else {
				
				if (System.nanoTime() - lastRender >= DESIRED_FPS_TIME) {
					
					renderTime = System.nanoTime();
					lastRender = renderTime;
					this.render();
					currentFPS++;
					renderTime = System.nanoTime() - renderTime;
					
					long diff = DESIRED_FPS_TIME - renderTime - updateTime - Math.max(0, overSleep);
					
					if (diff >= 1_000) {
						sleep(diff);
					}
				}
				
			}
					
			if (System.nanoTime() - timer >= SECOND_IN_NANOS) {
				
				if (EngineState.DEBUG_FPS) {
					EngineLog.info("UPS: " + currentUPS +"\tFPS: " + currentFPS);
				}
				
				currentUPS = 0;
				currentFPS = 0;
				
				timer = System.nanoTime();
				
			}
		
			afterTime = System.nanoTime();
			
		}
		
		this.cleanUp();
	}
	
	

	
	
	/**
	 * The update method of application, it will be executed according to Engine.UPS in a second.
	 * 
	 * @param deltatime how much time passed between the updates in MILLISECONDS
	 */
	protected abstract void update(long deltatime);
	
	
	protected abstract void render();
	
	
	private void cleanUp() {
		
	}
	
	
	public void stop() {
		isRunning = false;
	}
	
	
	private void sleep(long time) {
	
		try {
			long beforeSleep = System.nanoTime();
			Thread.sleep(time / 1_000_000L, (int) (time % 1_000_000L));
			this.overSleep = System.nanoTime() - beforeSleep - time;
		} catch (InterruptedException e) {
			EngineLog.severe("Error on sleeping " + e.getMessage());
			//e.printStackTrace();
		}
	}
	
}
