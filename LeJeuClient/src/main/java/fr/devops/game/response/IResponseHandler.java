package fr.devops.game.response;

import fr.devops.shared.network.response.IResponse;
import fr.devops.shared.service.IService;

public interface IResponseHandler extends IService{
	
	public void handleResponse(IResponse response);
	
}