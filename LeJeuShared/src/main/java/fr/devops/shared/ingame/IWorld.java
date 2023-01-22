package fr.devops.shared.ingame;

import java.util.List;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.event.IngameEventListener;

public interface IWorld extends IngameEventListener {

	public List<Entity> getEntities();
	
}
