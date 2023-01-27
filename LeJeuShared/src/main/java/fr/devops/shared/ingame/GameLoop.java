package fr.devops.shared.ingame;

import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.render.IWorldRenderer;
import fr.devops.shared.service.ServiceManager;

public class GameLoop {

	private boolean running;

	private boolean stopNextTick;

	private int targetFPS = 60;

	private final IWorld world;

	private final IWorldRenderer renderer;

	private int lastFPS = 0;

	public GameLoop(IWorld world, IWorldRenderer renderer) {
		this.world = world;
		this.renderer = renderer;
	}

	public synchronized void start() {
		running = true;
		long nextFrame = System.nanoTime();
		long nextTick = nextFrame;
		long frameSize = 0;
		if (targetFPS > 0) {
			frameSize = 1000000000 / targetFPS;
		}
		long tickSize = 50000000;
		long nextSec = nextFrame + 1000000000;
		long elapsed = 0;
		int nbFPS = 0;
		while (running) {
			var now = System.nanoTime();
			if (nextTick <= now) {
				nextTick = now + tickSize + (nextTick - now);
				masterTick();
			}
			if (renderer != null) {
				if (nextFrame <= now) {
					elapsed = frameSize + (now - nextFrame);
					nextFrame = now + frameSize + (nextFrame - now);
					nbFPS++;
					render(elapsed);
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

	public synchronized int getCurrentFPS() {
		return lastFPS;
	}

	public synchronized int getTargetFPS() {
		return targetFPS;
	}

	public synchronized void setTargetFPS(int targetFPS) {
		this.targetFPS = targetFPS;
	}

	public synchronized void stop() {
		stopNextTick = true;
	}

	public boolean isRunning() {
		return running;
	}

	private void masterTick() {
		var evtService = ServiceManager.get(IIngameEventService.class);
		if (evtService != null) {
			evtService.pollEvents(world);
			evtService.clearEventQueue();
		}
		world.tick();
	}

	public void render(long elapsed) {
		renderer.renderWorld(world, elapsed, lastFPS);
	}

}
