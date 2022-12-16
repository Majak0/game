package fr.devops.shared.ingame.entity;

public class Entity {

	private EntityType type;
	
	private double x;
	
	private double y;
	
	private double xspeed;
	
	private double yspeed;
	
	public EntityType getEntityType() {
		return type;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getXspeed() {
		return xspeed;
	}
	
	public double getYspeed() {
		return yspeed;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
}
