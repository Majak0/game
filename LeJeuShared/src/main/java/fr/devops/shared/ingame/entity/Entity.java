package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.sync.EntityProperty;
import fr.devops.shared.sync.SyncEntityProperty;

public abstract class Entity {
	
	private static int lastId = 0;
	
	public static int nextFreeId() {
		return ++lastId;
	}
	
	@SyncEntityProperty
	private final EntityProperty<Double> x = new EntityProperty<Double>(0d);
	
	@SyncEntityProperty
	private final EntityProperty<Double> y = new EntityProperty<Double>(0d);
	
	private int id;
	
	private boolean isOwned;
	
	public abstract EntityType getEntityType();
	
	public final double getX() {
		return x.getValue();	
	}
	
	public final double getY() {
		return y.getValue();
	}
	
	public final void setX(double x) {
		this.x.setValue(x);
	}
	
	public final void setY(double y) {
		this.y.setValue(y);
	}
	
	public boolean isOwned() {
		return isOwned;
	}
	
	public void setOwned(boolean owned) {
		isOwned = owned;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void tick(IWorld world) {
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder(getEntityType().toString());
		builder.append(' ');
		builder.append(id);
		builder.append(" (");
		builder.append(getX());
		builder.append(", ");
		builder.append(getY());
		builder.append(')');
		return builder.toString();
		
	}
}
