package fr.devops.shared.render;

import fr.devops.shared.ingame.IWorld;

public interface IRenderer {

	public void renderWorld(IWorld world, long elapsed, int lastFPS);
	
}
