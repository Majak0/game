package fr.devops.game.ingame.world;

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
import fr.devops.shared.network.request.EntityDestroyRequest;
import fr.devops.shared.network.request.EntitySpawnRequest;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.IEntitySyncManager;

public class World implements IWorld {

	private final List<Entity> entities = new LinkedList<>();

	@Override
	public void onPlayerJoined() {
	}

	@Override
	public void onPlayerLeaved() {
	}

	@Override
	public void onEntityCreated(EntityCreatedEvent event) {
		 var entity = event.type().makeNew();
		 entity.setId(event.id());
		 synchronized (entities) {
			 entities.add(entity);
		}
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		 synchronized (entities) {
			 entities.removeIf(e -> e.getId() == event.id());
		}
	}

	@Override
	public void onEntityModified(EntityModifiedEvent event) {
		Entity target = null;
		for (var entity : getEntities()) {
			if (entity.getId() == event.entityId()) {
				target = entity;
			}
		}
		if (target != null) {
			ServiceManager.get(IEntitySyncManager.class).applyChanges(target, event.valuesChanges());
		}
	}
	
	@Override
	public void onOwnerSet(OwnerSetEvent event) {
		for (var entity : getEntities()) {
			if (entity.getId() == event.entityId()) {
				entity.setOwned(true);
				break;
			}
		}
	}

	@Override
	public Entity[] getEntities() {
		synchronized (entities) {
			return entities.toArray(Entity[]::new);
		}
	}

	@Override
	public Entity spawn(EntityType type, double x, double y) {
		ServiceManager.get(INetworkService.class).send(new EntitySpawnRequest(type, x, y));
		return null;
	}
	
	@Override
	public void destroy(int entityId) {
		ServiceManager.get(INetworkService.class).send(new EntityDestroyRequest(entityId));
	}

	@Override
	public void tick() {
		for (var e : getEntities()) {
			if (e.isOwned()) {
				e.tick(this);
			}
		}
	}
	
}
