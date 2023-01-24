package fr.devops.server.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;

public class World implements IWorld {

	private List<Entity> entities = new LinkedList<>();
	
	public List<Entity> getEntities(){
		return entities;
	}

	@Override
	public void onPlayerJoined() {
		EntityType.TEST.makeNew();
	}

	@Override
	public void onPlayerLeaved() {
	}

	@Override
	public void onEntityCreated(EntityCreatedEvent event) {
		entities.add(event.type().makeNew());event.id();
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		entities.removeIf(e -> e.getId() == event.id());
	}

	@Override
	public void onEntityMove(EntityMoveEvent event) {
		
	}

	@Override
	public Entity spawn(EntityType type, int x, int y) {
		var entity = type.makeNew();
		entity.setX(x);
		entity.setY(y);
		entity.setId(Entity.nextFreeId());
		return entity; //TODO send to client.
	}
	
}
