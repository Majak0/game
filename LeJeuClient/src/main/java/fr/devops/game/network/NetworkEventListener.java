package fr.devops.game.network;

import fr.devops.shared.ingame.event.IngameEvent;

public interface NetworkEventListener {

	public void onIngameEvent(IngameEvent evt);
	
}
