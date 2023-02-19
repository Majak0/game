package fr.devops.game.menu;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.AuthenticationRequest;
import fr.devops.shared.network.response.AuthenticationFailureResponse;
import fr.devops.shared.service.ServiceManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements IController{
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML Label errorOutput;
	
	@FXML
	public void onConnect() {
		final var username = usernameField.getText();
		final var password = passwordField.getText();
		errorOutput.setText("Authentification en cours...");
		new Thread(() -> ServiceManager.get(INetworkService.class).send(new AuthenticationRequest(username, password))).start();
	}
	
	public void onRegister() {
		ServiceManager.get(NavigationService.class).goTo(Page.SIGNUP);
	}
	
	/**
	 * Un système pas très au point, j'aurai pus/dû généraliser avec de la généricité pour un systeme typeSafe mais le temps me manque.
	 */
	@Override
	public void receiveData(String key, Object value) {
		switch (key) {
		case NavigationService.AUTHENTICATION_FAILURE_MESSAGE:
			if (value instanceof AuthenticationFailureResponse authFailure) {
				Platform.runLater(() -> errorOutput.setText(authFailure.failureMessage()));
			}
		}
	}
	
}
