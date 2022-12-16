package fr.devops.shared.ingame;

import fr.devops.shared.render.IRenderer;

public class GameLoop {

	private boolean running;

	private boolean stopNextTick;
	
	private int targetFPS = 60;

	private final IWorld world;
	
	private final IRenderer renderer;
	
	public GameLoop(IWorld world, IRenderer renderer) {
		this.world = world;
		this.renderer = renderer;
	}
	
	public void start() {
		running = true;
		long lastFrame;
		long lastTick;
		long frameSize;
		long tickSize;
		// TODO HERE
		while (running) {
			
			if (stopNextTick) {
				running = false;
			}
		}
	}
	
	public int getTargetFPS() {
		return targetFPS;
	}
	
	public void setTargetFPS(int targetFPS) {
		this.targetFPS = targetFPS;
	}

	public synchronized void stop() {
		stopNextTick = true;
	}

	public boolean isRunning() {
		return running;
	}
	
	public void tick() {
		
	}
	
	public void render() {
		
	}

}
