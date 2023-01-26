package fr.devops.server.request;

import fr.devops.server.network.IClientContainer;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.request.AllEntitiesRequest;
import fr.devops.shared.ingame.request.EntitySpawnRequest;
import fr.devops.shared.ingame.request.IRequest;
import fr.devops.shared.service.ServiceManager;

public class RequestHandler implements IRequestHandler {

	private final IWorld world;

	public RequestHandler(IWorld world) {
		this.world = world;
	}

	@Override
	public void handleRequest(IRequest request) {
		if (request instanceof EntitySpawnRequest entitySpawn) {
			world.spawn(entitySpawn.type(), entitySpawn.x(), entitySpawn.y());
		} else if (request instanceof AllEntitiesRequest allEntities) {
			var client = ServiceManager.get(IClientContainer.class).get(allEntities.clientId());
			var entities = world.getEntities();
			synchronized(entities) {
				for (var entity : entities) {
					client.send(new EntityCreatedEvent(entity));
				}
			}
		}
	}

}
