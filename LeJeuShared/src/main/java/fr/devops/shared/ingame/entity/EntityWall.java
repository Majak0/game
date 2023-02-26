package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.collision.BoundingBox;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntityProperty;
import fr.devops.shared.sync.SyncEntityProperty;

public class EntityWall extends BoundableEntity {

	public static final double size = 20;
	
	@Override
	protected BoundingBox makeBoundingBox() {
		return new BoundingBox(0, 0, size, size);
	}
	@Override
	public EntityType getEntityType() {
		return EntityType.WALL;
	}
	
	@Override
	public void tick(IWorld world) {
	}

}
