package fr.devops.server.request;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.request.EntitySpawnRequest;
import fr.devops.shared.ingame.request.IRequest;

public class RequestHandler implements IRequestHandler{

	private final IWorld world;

	public RequestHandler(IWorld world) {
		this.world = world;
	}
	
	@Override
	public void handleRequest(IRequest request) {
		if (request instanceof EntitySpawnRequest entitySpawn) {
			world.spawn(entitySpawn.type(), entitySpawn.x(), entitySpawn.y());
		}
	}

}
