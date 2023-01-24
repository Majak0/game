package fr.devops.server.network;

import fr.devops.shared.service.IService;

public interface IServerNetworkService extends IService {
	
	public void sendToAll(Object payload);
	
	public void startListening(int port);
	
}