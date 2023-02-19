package fr.devops.server.sql;

import fr.devops.shared.service.IService;

public interface ISQLService extends IService{

	public boolean authenticate(String username, String password) throws Exception;
	
}
