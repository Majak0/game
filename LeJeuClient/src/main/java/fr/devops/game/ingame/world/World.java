package fr.devops.game.ingame.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;

public class World implements IWorld{

	private final List<Entity> entities = new LinkedList<>();
	
	@Override
	public void onPlayerJoined() {
	}

	@Override
	public void onPlayerLeaved() {
	}

	@Override
	public void onEntityCreated(EntityCreatedEvent event) {
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
	}

	@Override
	public void onEntityMove(EntityMoveEvent event) {
	}

	@Override
	public List<Entity> getEntities() {
		return entities;
	}

	@Override
	public Entity spawn(EntityType type, int x, int y) {
		//TODO request spawn to server;
		return null;
	}

}
