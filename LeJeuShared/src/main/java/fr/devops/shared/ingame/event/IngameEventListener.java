package fr.devops.shared.ingame.event;

public interface IngameEventListener {

	public void onPlayerJoined();
	
	public void onPlayerLeaved();
	
	public void onEntityCreated(EntityCreatedEvent event);
	
	public void onEntityDestroyed(EntityDestroyedEvent event);
	
	public void onEntityModified(EntityModifiedEvent event);
	
}