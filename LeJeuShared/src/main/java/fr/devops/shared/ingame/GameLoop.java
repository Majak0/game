package fr.devops.shared.ingame;

import fr.devops.shared.render.IRenderer;

public class GameLoop {

	private boolean running;

	private boolean stopNextTick;

	private int targetFPS = 60;

	private final IWorld world;

	private final IRenderer renderer;

	private int lastFPS = 0;

	public GameLoop(IWorld world, IRenderer renderer) {
		this.world = world;
		this.renderer = renderer;
	}

	public void start() {
		running = true;
		long nextFrame = System.nanoTime();
		long nextTick = nextFrame;
		long frameSize = 1000000000 / targetFPS;
		long tickSize = 50000000;
		long nextSec = nextFrame + 1000000000;
		int nbFPS = 0;
		while (running) {
			var now = System.nanoTime();
			if (nextTick <= now) {
				nextTick = now + tickSize + (nextTick - now);
				tick();
			}
			if (renderer != null) {
				if (nextFrame <= now) {
					nextFrame = now + frameSize + (nextFrame - now);
					nbFPS++;
					render();
				}
				if (nextSec <= now) {
					nextSec = now + 1000000000;
					lastFPS = nbFPS;
					nbFPS = 0;
				}
			}
			if (stopNextTick) {
				running = false;
			}
		}
	}

	public int getCurrentFPS() {
		return lastFPS;
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
