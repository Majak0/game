package fr.devops.shared.ingame.entity;

import fr.devops.shared.ingame.IWorld;

public class EntityTest extends Entity {

	private int size = 20;
	
	@Override
	public EntityType getEntityType() {
		return EntityType.TEST;
	}
	
	
	/**
	 * Obtient la taille en pixel.
	 * @return
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Définit la taille en pixel.
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public void tick(IWorld world) {
		var x = getX();
		if (++x > 300) {
			x = 0;
		}
		setX(x);
	}

}
