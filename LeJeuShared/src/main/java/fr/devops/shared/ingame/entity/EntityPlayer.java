package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.collision.BoundingBox;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntityProperty;

public class EntityPlayer extends BoundableEntity {

	private static final double speed = 10;
	
	private static final double size = 20;
	
	private EntityProperty<Double> xspeed = new EntityProperty<>(0d);
	
	private EntityProperty<Double> yspeed = new EntityProperty<>(0d);
	
	
	@Override
	protected BoundingBox makeBoundingBox() {
		return new BoundingBox(0, 0, size, size);
	}
	
	public double getXSpeed() {
		return xspeed.getValue();
	}
	
	public void setXSpeed(double value) {
		xspeed.setValue(value);
	}
	
	public double getYSpeed() {
		return yspeed.getValue();
	}
	
	public void setYSpeed(double value) {
		yspeed.setValue(value);
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}
	
	@Override
	public void tick(IWorld world) {
		var playerController = ServiceManager.get(IPlayerController.class);
		if (playerController != null) {
			setXSpeed(playerController.getXAxis() * speed);
		}
		// Apply movement
		setX(getX() + getXSpeed());
		setY(getY() + getYSpeed());
	}

}
