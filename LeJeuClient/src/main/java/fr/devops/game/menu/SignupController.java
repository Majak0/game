package fr.devops.game.menu;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.RegisterUserRequest;
import fr.devops.shared.service.ServiceManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignupController implements IController{
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private TextField passwordConfirmField;
	
	@FXML
	private Label errorOutput;
	
	@FXML
	public void onRegister() {
		final var username = usernameField.getText();
		final var password = passwordField.getText();
		// VERIFICATIONS
		if (username.length() > 20) {
			errorOutput.setText("Le nom d'utilisateur doit faire moins de 21 charactere.");
			return;
		}
		if (password.length() > 20) {
			errorOutput.setText("Le mot de passe doit faire moins de 21 charactere.");
			return;
		}
		if (!password.contentEquals(passwordConfirmField.getText())) {
			errorOutput.setText("La confirmation du mot de passe n'est pas égale au mot de passe.");
			return;
		}
		// REGISTER
		errorOutput.setText("Création de l'utilisateur en ligne...");
		new Thread(()->ServiceManager.get(INetworkService.class).send(new RegisterUserRequest(username, password))).start();
	}
	
	@FXML
	public void onNavigateBack() {
		ServiceManager.get(NavigationService.class).goTo(Page.LOGIN);
	}
	
	@Override
	public void receiveData(String key, Object value) {
		switch (key) {
		case NavigationService.REGISTER_USER_RESPONSE_MESSAGE:
			if (value instanceof String registerUserResponseMessage) {
				Platform.runLater(() -> errorOutput.setText(registerUserResponseMessage));
			}
		}
	}
	
}
