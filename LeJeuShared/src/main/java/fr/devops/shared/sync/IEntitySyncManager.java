package fr.devops.shared.sync;

import java.util.Map;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.service.IService;

public interface IEntitySyncManager extends IService{

	public void sendChanges(Entity entity);
	
	public void sendCreation(Entity entity);
	
	public void applyChanges(Entity entity, Map<String, Object> event);
	
}
