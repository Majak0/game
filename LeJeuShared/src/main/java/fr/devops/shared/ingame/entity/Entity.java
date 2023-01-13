package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;

public abstract class Entity {

	private EntityType type;
	
	private double x;
	
	private double y;
	
	public EntityType getEntityType() {
		return type;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public abstract void tick(IWorld world);
	
	public abstract void render(int context, int width, int height);
	
}
