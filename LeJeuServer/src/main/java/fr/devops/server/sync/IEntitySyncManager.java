package fr.devops.server.sync;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.service.IService;

public interface IEntitySyncManager extends IService{

	public void sendChanges(Entity entity);
	
}
