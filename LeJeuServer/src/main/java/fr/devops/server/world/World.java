package fr.devops.server.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityModifiedEvent;
import fr.devops.shared.ingame.event.OwnerSetEvent;
import fr.devops.shared.network.INetworkService;
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
		throw new RuntimeException("Clients should never call onEntityCreated on server world, please use EntitySpawnRequest");
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		throw new RuntimeException("Clients should never call onEntityDestroyed on server world, please use EntityDestroyRequest");
	}

	@Override
	public void onEntityModified(EntityModifiedEvent event) {
		var syncManager = ServiceManager.get(IEntitySyncManager.class);
		for (var entity : getEntities()) {
			if (entity.getId() == event.entityId()) {
				syncManager.applyChanges(entity, event.valuesChanges());
				break;
			}
		}
	}
	
	@Override
	public void onOwnerSet(OwnerSetEvent event) {
		// NO NEED
	}

	@Override
	public Entity spawn(EntityType type, double x, double y) {
		var entity = type.makeNew();
		entity.setX(x);
		entity.setY(y);
		entity.setId(Entity.nextFreeId());
		entity.setOwned(true);
		synchronized (entities) {
			entities.add(entity);
		}
		ServiceManager.get(IEntitySyncManager.class).sendCreation(entity);
		return entity;
	}
	
	@Override
	public void destroy(int entityId) {
		synchronized (entities) {
			entities.removeIf(e -> e.getId() == entityId);
		}
		ServiceManager.get(INetworkService.class).send(new EntityDestroyedEvent(entityId));
	}
	
	@Override
	public void tick() {
		var entities = getEntities();
		for (var e : entities) {
			if (e.isOwned()) {
				e.tick(this);
			}
		}
		var syncManager = ServiceManager.get(IEntitySyncManager.class);
		for (var e : entities) {
			syncManager.sendChanges(e);
		}
	}

}
