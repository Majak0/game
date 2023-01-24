package fr.devops.server.network;

import fr.devops.shared.ingame.event.IngameEvent;

public interface ServerNetworkEventListener {

	public void onNetworkIngameEvent(IngameEvent evt);
	
}
