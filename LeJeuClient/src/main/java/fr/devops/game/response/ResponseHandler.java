package fr.devops.game.response;

import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.network.response.AuthenticationFailureResponse;
import fr.devops.shared.network.response.AuthenticationSuccessResponse;
import fr.devops.shared.network.response.IResponse;
import fr.devops.shared.network.response.RegisterUserResponse;
import fr.devops.shared.service.ServiceManager;

public class ResponseHandler implements IResponseHandler{

	@Override
	public void handleResponse(IResponse response) {
		
		if (response instanceof AuthenticationSuccessResponse authSuccess) {
			Entity.localOwnerId = authSuccess.localPlayerId();
			ServiceManager.get(NavigationService.class).UiThreadGoTo(Page.INGAME);
		}else if (response instanceof AuthenticationFailureResponse authFailure) {
			ServiceManager.get(NavigationService.class).sendDataToController(NavigationService.AUTHENTICATION_FAILURE_MESSAGE, authFailure);
		}else if (response instanceof RegisterUserResponse registerUser) {
			ServiceManager.get(NavigationService.class).sendDataToController(NavigationService.REGISTER_USER_RESPONSE_MESSAGE, registerUser.message());
		}
	}

}
