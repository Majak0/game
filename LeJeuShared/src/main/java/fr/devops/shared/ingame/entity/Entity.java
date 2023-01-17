package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;

public abstract class Entity {
	
	private double x;
	
	private double y;
	
	public abstract EntityType getEntityType();
	
	public final double getX() {
		return x;	
	}
	
	public final double getY() {
		return y;
	}
	
	public final void setX(double x) {
		this.x = x;
	}
	
	public final void setY(double y) {
		this.y = y;
	}
	
	public void tick(IWorld world) {
	}
}
