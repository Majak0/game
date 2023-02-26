package fr.devops.server.request;

import fr.devops.server.network.IClientContainer;
import fr.devops.server.sql.ISQLService;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.AllEntitiesRequest;
import fr.devops.shared.network.request.AuthenticationRequest;
import fr.devops.shared.network.request.EntityDestroyRequest;
import fr.devops.shared.network.request.EntitySpawnRequest;
import fr.devops.shared.network.request.IRequest;
import fr.devops.shared.network.request.RegisterUserRequest;
import fr.devops.shared.network.response.AuthenticationFailureResponse;
import fr.devops.shared.network.response.AuthenticationSuccessResponse;
import fr.devops.shared.network.response.RegisterUserResponse;
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
		}else if (request instanceof EntityDestroyRequest entityDestroy) {
			world.destroy(entityDestroy.entityId());
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
						var client = ServiceManager.get(IClientContainer.class).get(clientId);
						client.send(new AuthenticationSuccessResponse(clientId));
						var entity = world.spawn(EntityType.PLAYER, 100, 100);
						// Transfert ownership to player
						entity.setOwnerId(clientId);
					}else {
						throw new Exception("Nom d'utilisateur ou mot de passe invalide.");
					}
				}catch(Exception e) {
					ServiceManager.get(INetworkService.class).send(new AuthenticationFailureResponse(e.getMessage()));
				}
			}).start();
		}else if (request instanceof RegisterUserRequest registerUser) {
			new Thread(() -> {
				try {
					if (ServiceManager.get(ISQLService.class).register(registerUser.username(), registerUser.password())) {
						var client = ServiceManager.get(IClientContainer.class).get(clientId);
						client.send(new RegisterUserResponse("L'utilisateur à bien été créé."));
					}else {
						throw new Exception("Ce nom d'utilisateur est déja pris.");
					}
				}catch(Exception e) {
					ServiceManager.get(INetworkService.class).send(new RegisterUserResponse(e.getMessage()));
				}
			}).start();
		}
	}

}
