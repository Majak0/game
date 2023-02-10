package fr.devops.shared.ingame.entity;

import fr.devops.shared.GameSide;
import fr.devops.shared.ingame.IWorld;

public class EntityTest extends Entity {

	@Override
	public EntityType getEntityType() {
		return EntityType.TEST;
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
