package fr.devops.server.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityModifiedEvent;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.IEntitySyncManager;

public class World implements IWorld {

	private List<Entity> entities = new LinkedList<>();

	public Entity[] getEntities() {
		synchronized (entities) {
			return entities.toArray(Entity[]::new);
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
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		synchronized (entities) {
			entities.removeIf(e -> e.getId() == event.id());
		}
	}

	@Override
	public void onEntityModified(EntityModifiedEvent event) {
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
		ServiceManager.get(IEntitySyncManager.class).sendCreation(entity);
		System.out.println("world created entity " + entity);
	}
	
	@Override
	public void tick() {
		var entities = getEntities();
		for (var e : entities) {
			e.tick(this);
		}
		var syncManager = ServiceManager.get(IEntitySyncManager.class);
		for (var e : entities) {
			syncManager.sendChanges(e);
		}
	}

}
