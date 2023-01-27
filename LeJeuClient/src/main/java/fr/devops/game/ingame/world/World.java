package fr.devops.game.ingame.world;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;
import fr.devops.shared.ingame.request.EntitySpawnRequest;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;

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
		/*
		 * var entity = event.type().makeNew(); entity.setId(event.id());
		 * entity.setX(event.x()); entity.setY(event.y()); entities.add(entity);
		 */
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
	}

	@Override
	public void onEntityMove(EntityMoveEvent event) {
	}

	@Override
	public List<Entity> getEntities() {
		synchronized (entities) {
			return entities;
		}
	}

	@Override
	public void spawn(EntityType type, double x, double y) {
		ServiceManager.get(INetworkService.class).send(new EntitySpawnRequest(type, x, y));
	}

	@Override
	public void tick() {
		for (var e : getEntities().toArray(Entity[]::new)) {
			e.tick(this);
		}
	}
	
}
