package fr.devops.shared.render;

import fr.devops.shared.ingame.IWorld;

public interface IWorldRenderer {

	public void renderWorld(IWorld world, long elapsed, int lastFPS);
	
}
