package fr.devops.server;

import fr.devops.server.event.ServerIngameEventService;
import fr.devops.server.network.ServerNetworkService;
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
	
	public Main() {
		setupServices();
		var world = new World();
		var loop = new GameLoop(world, null);
		loop.start();
	}
	
	private void setupServices() {
		ServiceManager.registerAs(INetworkService.class,new ServerNetworkService()); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
	}
	
}
