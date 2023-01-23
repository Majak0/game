package fr.devops.server;

import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.service.ServiceManager;

public class Main {

	public static void main(String[] args) {
		var main = new Main();
	}
	
	public Main() {
		setupServices();
	}
	
	private void setupServices() {
		ServiceManager.registerAs(INetworkService.class,new NetworkService()); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		ServiceManager.register(new EntityRendererContainer());
	}
	
}
