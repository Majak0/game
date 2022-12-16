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
		long lastFrame  = System.nanoTime();
		long lastTick = System.nanoTime();
		long frameSize = 1000000000 / targetFPS;
		long tickSize =  50000000;
		
		while (running) {
			var now = System.nanoTime();
			if (now - lastTick > tickSize) {
				lastTick = now;
				tick();
			}
			if (now - lastFrame > frameSize) {
				lastFrame = now;
				render();
			}
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
		for (var e : world.getEntities()) {
			e.tick(world);
		}
	}
	
	public void render() {
		renderer.renderWorld(world);
	}

}
