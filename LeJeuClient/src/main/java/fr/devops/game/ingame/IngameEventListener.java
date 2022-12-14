package fr.devops.game.ingame;

public interface IngameEventListener {

	public void onPlayerJoined();
	
	public void onPlayerLeaved();
	
	public void onEntityMove();
	
}
