package fr.devops.server.event;

import fr.devops.server.network.ServerNetworkEventListener;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.ingame.event.IngameEventListener;

public class ServerIngameEventService implements IIngameEventService, ServerNetworkEventListener{

	@Override
	public void pollEvents(IngameEventListener listener) {
	}

	@Override
	public void clearEventQueue() {
	}

	@Override
	public void sendEventToNetwork(IngameEvent event) {
	}

	@Override
	public void onNetworkIngameEvent(IngameEvent evt) {
	}

}
