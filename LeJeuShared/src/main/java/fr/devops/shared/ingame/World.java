package fr.devops.shared.ingame;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityDestroyedEvent;
import fr.devops.shared.ingame.event.EntityMoveEvent;

public class World implements IWorld {

	private List<Entity> entities = new LinkedList<>();
	
	public List<Entity> getEntities(){
		return entities;
	}

	@Override
	public void onPlayerJoined() {
		EntityType.TEST.makeNew();
	}

	@Override
	public void onPlayerLeaved() {
	}

	@Override
	public void onEntityCreated(EntityCreatedEvent event) {
		entities.add(event.type().makeNew());
	}

	@Override
	public void onEntityDestroyed(EntityDestroyedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityMove(EntityMoveEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
