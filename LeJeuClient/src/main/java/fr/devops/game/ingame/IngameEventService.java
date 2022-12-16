package fr.devops.game.ingame;

import java.util.LinkedList;
import java.util.List;

import fr.devops.game.network.INetworkService;
import fr.devops.game.network.NetworkEventListener;
import fr.devops.game.util.WeakLinkedList;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;
import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.ingame.event.IngameEventListener;
import fr.devops.shared.service.IService;
import fr.devops.shared.service.ServiceManager;

public class IngameEventService implements IService, NetworkEventListener {

	private List<IngameEventListener> listeners = new WeakLinkedList<>();
	
	private List<IngameEvent> fromServerPool = new LinkedList<>();
	
	/**
	 * Envoie les évenements aux listeners.
	 */
	public void pollEvents() {
		synchronized(fromServerPool) {
			for (var event : fromServerPool) {
				if (event instanceof EntityMoveEvent evt) {
					for (var listener : listeners) {
						listener.onEntityMove(evt);
					}
				}else if (event instanceof EntityCreatedEvent evt) {
					for (var listener : listeners) {
						listener.onEntityCreated(evt);
					}
				}else if (event instanceof EntityDestroyedEvent evt) {
					for (var listener : listeners) {
						listener.onEntityDestroyed(evt);
					}
				}
			}
			fromServerPool.clear();
		}
	}
	
	public void linkToNetwork(INetworkService service) {
		service.registerListener(this);
	}
	
	public void sendToNetwork(IngameEvent event) {
		ServiceManager.get(INetworkService.class).send(event);
	}
	
	public void register(IngameEventListener listener) {
		listeners.add(listener);
	}
	
	public void unregister(IngameEventListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void onIngameEvent(IngameEvent evt) {
		synchronized(fromServerPool) {
			fromServerPool.add(evt);
		}
	}
	
}