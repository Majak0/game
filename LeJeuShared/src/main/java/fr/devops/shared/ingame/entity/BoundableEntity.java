package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.collision.BoundingBox;

public abstract class BoundableEntity extends Entity {
	
	private BoundingBox boundingBox = makeBoundingBox();
	
	public boolean overlap(BoundableEntity other) { // Algo classique de collision de boite (sans rotation)
		var otherBound = other.getBoundingBox();
		var otherLeft = other.getX() + otherBound.getX();
		var otherRight = other.getX() + otherBound.getX() + otherBound.getWidth();
		var otherUp = other.getY() + otherBound.getY();
		var otherDown = other.getY() + otherBound.getY() + otherBound.getWidth();
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
