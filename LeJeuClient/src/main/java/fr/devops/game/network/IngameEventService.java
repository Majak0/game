package fr.devops.game.network;

import fr.devops.game.service.IService;
import fr.devops.game.service.ServiceManager;

public class IngameEventService implements IService {

	private void sendToNetwork(Object payload) {
		ServiceManager.get(INetworkService.class).send(payload);
	}
	
}