package fr.devops.server.request;

import fr.devops.shared.ingame.request.IRequest;
import fr.devops.shared.service.IService;

public interface IRequestHandler extends IService{

	public void handleRequest(IRequest request);
	
}
