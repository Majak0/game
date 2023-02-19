package fr.devops.server.request;

import fr.devops.shared.network.request.IRequest;
import fr.devops.shared.service.IService;

public interface IRequestHandler extends IService{

	public void handleRequest(int clientId, IRequest request);
	
}
