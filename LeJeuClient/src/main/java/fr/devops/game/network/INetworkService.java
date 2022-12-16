package fr.devops.game.network;

import fr.devops.shared.service.IService;

public interface INetworkService extends IService {
	
	/**
	 * Blocking call, try to connect to the server.
	 * This method is thread safe.
	 * @param address URL
	 * @param port Port de connexion.
	 * @return Successfull connection.
	 */
	public boolean connect(String address, int port);
	
	/**
	 * Ferme la connexion avec le serveur s'il est connecté.
	 * Cet appel est bloquant.
	 */
	public void disconnect();
	
	/**
	 * Send payload to the server.
	 * Throw {@link IllegalStateException} if the service is not connected.
	 * This method is not blocking and is thread safe.
	 * @param payload
	 */
	public void send(Object payload);
	
	public void registerListener(NetworkEventListener listener);
	
}
