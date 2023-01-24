package fr.devops.shared.network;

import fr.devops.shared.ingame.event.IngameEvent;

public interface INetworkEventListener {

	public void onNetworkIngameEvent(IngameEvent evt);
	
}
