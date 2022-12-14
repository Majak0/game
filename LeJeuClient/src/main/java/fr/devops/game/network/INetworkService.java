package fr.devops.game.network;

import fr.devops.game.service.IService;

public interface INetworkService extends IService {
	
	/**
	 * Blocking call, try to connect to the server.
	 * This method is thread safe.
	 * @param address URL
	 * @return Successfull connection.
	 */
	public boolean connect(String address);
	
	/**
	 * Send payload to the server.
	 * Throw {@link IllegalStateException} if the service is not connected.
	 * This method is not blocking and is thread safe.
	 * @param payload
	 */
	public void send(Object payload);
	
	public void registerListener(NetworkEventListener listener);
	
}
