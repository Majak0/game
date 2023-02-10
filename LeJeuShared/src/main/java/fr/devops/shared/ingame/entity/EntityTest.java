package fr.devops.shared.ingame.entity;

import fr.devops.shared.GameSide;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.sync.EntityProperty;

public class EntityTest extends Entity {

	private EntityProperty<Integer> size = new EntityProperty<>(20);
	
	@Override
	public EntityType getEntityType() {
		return EntityType.TEST;
	}
	
	
	/**
	 * Obtient la taille en pixel.
	 * @return
	 */
	public int getSize() {
		return size.getValue();
	}
	
	/**
	 * Définit la taille en pixel.
	 * @param size
	 */
	public void setSize(int size) {
		this.size.setValue(size);
	}
	
	@Override
	public void tick(IWorld world) {
		var x = getX();
		if (x >= 0) {
			if (++x > 300) {
				x = -1;
				if (GameSide.isServer()) {
					world.spawn(getEntityType(), x+1, getY() + 20);
				}
			}
			setX(x);
		}
	}

}
