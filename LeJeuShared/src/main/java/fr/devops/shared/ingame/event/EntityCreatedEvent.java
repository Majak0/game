package fr.devops.shared.ingame.event;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;

public record EntityCreatedEvent(int id, EntityType type, double x, double y) implements IngameEvent {
	
	public EntityCreatedEvent(Entity entity) {
		this(entity.getId(), entity.getEntityType(), entity.getX(), entity.getY());
	}
	
}