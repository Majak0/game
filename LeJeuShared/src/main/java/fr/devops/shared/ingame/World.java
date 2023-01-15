package fr.devops.shared.ingame;

import java.util.LinkedList;
import java.util.List;

import fr.devops.shared.ingame.entity.Entity;

public class World implements IWorld {

	private List<Entity> entities = new LinkedList<>();
	
	public List<Entity> getEntities(){
		return entities;
	}
	
}
