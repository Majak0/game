package fr.devops.server.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.service.ServiceManager;

public class World implements IWorld {

	private List<Entity> entities = new LinkedList<>();

	public List<Entity> getEntities() {
		synchronized (entities) {
			return entities;
		}
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
		entities.add(event.type().makeNew());
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		entities.removeIf(e -> e.getId() == event.id());
	}

	@Override
	public void onEntityMove(EntityMoveEvent event) {

	}

	@Override
	public void spawn(EntityType type, double x, double y) {
		var entity = type.makeNew();
		entity.setX(x);
		entity.setY(y);
		entity.setId(Entity.nextFreeId());
		synchronized (entities) {
			entities.add(entity);
		}
		ServiceManager.get(IIngameEventService.class).sendEventToNetwork(new EntityCreatedEvent(entity));
	}
	
	@Override
	public void tick() {
		for (var e : getEntities().toArray(Entity[]::new)) {
			e.tick(this);
			
		}
	}

}
