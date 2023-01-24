package fr.devops.server;

import fr.devops.server.network.ServerNetworkService;
import fr.devops.server.request.IRequestHandler;
import fr.devops.server.request.RequestHandler;
import fr.devops.server.world.World;
import fr.devops.shared.ingame.GameLoop;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	private final ServerNetworkService network = new ServerNetworkService();
	
	public Main() {
		ServiceManager.registerAs(INetworkService.class,network); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		var world = new World();
		ServiceManager.registerAs(IRequestHandler.class, new RequestHandler(world));
		var loop = new GameLoop(world, null);
		network.startListening(25565);
		loop.start();
	}
	
}
