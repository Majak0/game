package fr.devops.shared.ingame.event;

import fr.devops.shared.service.IService;

public interface IIngameEventService extends IService {

	public void pollEvents(IngameEventListener listener);
	
	public void clearEventQueue();
	
	public void sendEventToNetwork(IngameEvent event);
	
}
