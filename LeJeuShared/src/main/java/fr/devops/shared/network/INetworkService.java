package fr.devops.shared.network;

import fr.devops.shared.service.IService;

public interface INetworkService extends IService {
	
	/**
	 * Send payload to network.
	 * @param payload
	 */
	public void send(Object payload);
	
	public void registerListener(INetworkEventListener listener);
	
}
