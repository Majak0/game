package fr.devops.shared.ingame.event;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.network.INetworkEventListener;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;

public class IngameEventService implements INetworkEventListener, IIngameEventService{

	private List<IngameEvent> fromServerPool = new LinkedList<>();
	
	public IngameEventService() {
		ServiceManager.get(INetworkService.class).registerListener(this);
	}
	
	/**
	 * Envoie les évenements aux listeners.
	 */
	@Override
	public void pollEvents(IngameEventListener listener) {
		synchronized(fromServerPool) {
			for (var event : fromServerPool) {
				if (event instanceof EntityCreatedEvent evt) {
					listener.onEntityCreated(evt);
				}else if (event instanceof EntityDestroyedEvent evt) {
					listener.onEntityDestroyed(evt);
				}else if (event instanceof EntityModifiedEvent evt) {
					listener.onEntityModified(evt);
				}
			}
		}
	}
	
	@Override
	public void clearEventQueue() {
		synchronized(fromServerPool) {
			fromServerPool.clear();
		}
	}
	
	@Override
	public void sendEventToNetwork(IngameEvent event) {
		ServiceManager.get(INetworkService.class).send(event);
	}

	@Override
	public void onNetworkIngameEvent(IngameEvent evt) {
		synchronized(fromServerPool) {
			fromServerPool.add(evt);
		}
	}
	
}