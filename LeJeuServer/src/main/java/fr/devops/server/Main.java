package fr.devops.server;

import fr.devops.server.network.ClientContainer;
import fr.devops.server.network.IClientContainer;
import fr.devops.server.network.ServerNetworkService;
import fr.devops.server.request.IRequestHandler;
import fr.devops.server.request.RequestHandler;
import fr.devops.server.world.World;
import fr.devops.shared.GameSide;
import fr.devops.shared.ingame.GameLoop;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntitySyncManager;
import fr.devops.shared.sync.IEntitySyncManager;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	private final ServerNetworkService network = new ServerNetworkService();
	
	public Main() {
		GameSide.set(GameSide.SERVER);
		ServiceManager.registerAs(IEntitySyncManager.class, new EntitySyncManager());
		ServiceManager.registerAs(IClientContainer.class,new ClientContainer());
		ServiceManager.registerAs(INetworkService.class,network); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		var world = new World();
		world.spawn(EntityType.TEST, 10, 10);
		ServiceManager.registerAs(IRequestHandler.class, new RequestHandler(world));
		var loop = new GameLoop(world, null);
		network.startListening(25565);
		loop.start();
	}
	
}
