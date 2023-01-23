package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;

public abstract class Entity {
	
	private static int lastId = 0;
	
	public static int nextFreeId() {
		return ++lastId;
	}
	
	private double x;
	
	private double y;
	
	private int id;
	
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void tick(IWorld world) {
	}
}
