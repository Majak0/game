package fr.devops.shared.ingame.entity;

import java.util.Collection;
import java.util.LinkedList;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.collision.BoundingBox;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntityProperty;
import fr.devops.shared.sync.SyncEntityProperty;

public class EntityPlayer extends BoundableEntity {

	private static final double speed = 5;
	
	public static final double size = 20;
	
	@SyncEntityProperty
	private EntityProperty<Double> xspeed = new EntityProperty<>(0d);
	
	@SyncEntityProperty
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
		var xspd = getXSpeed();
		var yspd = getYSpeed();
		if (playerController != null) {
			xspd = playerController.getXAxis() * speed;
			if (playerController.isJumping()) {
				yspd = -10;
			}
		}
		// Apply movement
		var collider = new LinkedList<BoundableEntity>();
		for (var entity : world.getEntities()) {
			if (entity instanceof BoundableEntity bound && entity.getId() != getId()) {
				collider.add(bound);
			}
		}
		setYSpeed(++yspd); // gravity
		
		var boundingBox = getBoundingBox();
		var xsign = Math.signum(xspd);
		var ysign = Math.signum(yspd);
		var currX = getX();
		var currY = getY();
		var xobj = xspd + currX + xsign;
		var yobj = yspd + currY + ysign;
		System.out.println("xspd="+xspd);
		System.out.println("currX="+currX);
		System.out.println("xobj="+xobj);
		for (var x = currX; xspd > 0 ? x < xobj : x > xobj; x += xsign) {
			System.out.println("x="+x);
			if (anyOverlap(collider, boundingBox, x, currY)) {
				setXSpeed(0);
				break;
			}else {
				currX = x;
			}
		}
		for (var y = currY; yspd > 0 ? y < yobj : y > yobj; y += ysign) {
			if (anyOverlap(collider, boundingBox, currX, y)) {
				setYSpeed(0);
				break;
			}else {
				currY = y;
			}
		}
		setX(currX);
		setY(currY);
	}
	
	private boolean anyOverlap(Collection<BoundableEntity> others, BoundingBox boundingBox, double x, double y) {
		for (var o : others) {
			if (o.overlap(boundingBox, x, y)) {
				return true;
			}
		}
		return false;
	}

}
