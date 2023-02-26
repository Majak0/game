package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.collision.BoundingBox;

public abstract class BoundableEntity extends Entity {
	
	private BoundingBox boundingBox = makeBoundingBox();
	
	public boolean overlap(BoundingBox other, double x, double y) { // Algo classique de collision de boite (sans rotation)
		var otherLeft = x + other.getX();
		var otherRight = x + other.getX() + other.getWidth();
		var otherUp = y + other.getY();
		var otherDown = y + other.getY() + other.getWidth();
		var myLeft = getX() + boundingBox.getX();
		var myRight = getX() + boundingBox.getX() + boundingBox.getWidth();
		var myUp = getY() + boundingBox.getY();
		var myDown = getY() + boundingBox.getY() + boundingBox.getWidth();
		var hor = myRight >= otherLeft && myLeft <= otherRight;
		var vert = myUp <= otherDown && myDown >= otherUp;
		return hor && vert;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	protected abstract BoundingBox makeBoundingBox();
	
}
