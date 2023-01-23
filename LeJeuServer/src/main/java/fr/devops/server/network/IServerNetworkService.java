package fr.devops.server.network;

public interface IServerNetworkService {
	
	public void sendToAll(Object payload);
	
	public void startListening(int port);
	
}