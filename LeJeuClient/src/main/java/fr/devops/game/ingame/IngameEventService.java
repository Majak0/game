package fr.devops.game.ingame;

import java.util.LinkedList;
import java.util.List;

import fr.devops.game.network.INetworkService;
import fr.devops.game.network.NetworkEventListener;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.ingame.event.IngameEventListener;
import fr.devops.shared.service.ServiceManager;

public class IngameEventService implements NetworkEventListener, IIngameEventService{

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
				if (event instanceof EntityMoveEvent evt) {
					listener.onEntityMove(evt);
				}else if (event instanceof EntityCreatedEvent evt) {
					listener.onEntityCreated(evt);
				}else if (event instanceof EntityDestroyedEvent evt) {
					listener.onEntityDestroyed(evt);
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