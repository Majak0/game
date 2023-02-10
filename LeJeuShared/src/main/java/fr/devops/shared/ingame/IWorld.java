package fr.devops.shared.ingame;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.IngameEventListener;

public interface IWorld extends IngameEventListener {

	public Entity[] getEntities();
	
	/**
	 * Fait apparaitre une entitée dans le monde et lui donne un identifiant.
	 * @param type
	 * @param x
	 * @param y
	 * @return
	 */
	public void spawn(EntityType type, double x, double y);
	
	public void tick();
}
