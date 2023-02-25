package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntityProperty;

public class EntityPlayer extends Entity {

	private EntityProperty<Integer> xspeed = new EntityProperty<>(0);
	
	private EntityProperty<Integer> yspeed = new EntityProperty<>(0);
	
	public int getXSpeed() {
		return xspeed.getValue();
	}
	
	public void setXSpeed(int value) {
		xspeed.setValue(value);
	}
	
	public int getYSpeed() {
		return yspeed.getValue();
	}
	
	public void setYSpeed(int value) {
		yspeed.setValue(value);
	}
	
	private int speed = 10;
	
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
