package fr.devops.server.request;

import fr.devops.server.network.IClientContainer;
import fr.devops.server.sql.ISQLService;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.AllEntitiesRequest;
import fr.devops.shared.network.request.AuthenticationRequest;
import fr.devops.shared.network.request.EntitySpawnRequest;
import fr.devops.shared.network.request.IRequest;
import fr.devops.shared.network.response.AuthenticationFailureResponse;
import fr.devops.shared.network.response.AuthenticationSuccessResponse;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.IEntitySyncManager;

public class RequestHandler implements IRequestHandler {

	private final IWorld world;

	public RequestHandler(IWorld world) {
		this.world = world;
	}

	@Override
	public void handleRequest(int clientId, IRequest request) {
		if (request instanceof EntitySpawnRequest entitySpawn) {
			world.spawn(entitySpawn.type(), entitySpawn.x(), entitySpawn.y());
		} else if (request instanceof AllEntitiesRequest allEntities) {
			var client = ServiceManager.get(IClientContainer.class).get(clientId);
			for (var entity : world.getEntities()) {
				client.send(new EntityCreatedEvent(entity));
				ServiceManager.get(IEntitySyncManager.class).sendCreation(entity);
			}
		} else if (request instanceof AuthenticationRequest auth) {
			new Thread(() -> {
				try {
					if (ServiceManager.get(ISQLService.class).authenticate(auth.username(), auth.password())) {
						ServiceManager.get(INetworkService.class).send(new AuthenticationSuccessResponse());
					}else {
						throw new Exception("Nom d'utilisateur ou mot de passe invalide.");
					}
				}catch(Exception e) {
					ServiceManager.get(INetworkService.class).send(new AuthenticationFailureResponse(e.getMessage()));
				}
			}).start();
		}
	}

}
