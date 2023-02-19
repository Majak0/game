package fr.devops.game.menu;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.service.ServiceManager;
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
		final var password = usernameField.getText();
		// VERIFICATIONS
		if (username.length() > 20) {
			errorOutput.setText("Le nom d'utilisateur doit faire moins de 21 charactere.");
			return;
		}
		if (password.length() > 20) {
			errorOutput.setText("Le mot de passe doit faire moins de 21 charactere.");
			return;
		}
		if (!username.contentEquals(passwordConfirmField.getText())) {
			errorOutput.setText("La confirmation du mot de passe n'est pas égale au mot de passe.");
			return;
		}
		// REGISTER
	}
	
	@FXML
	public void onNavigateBack() {
		ServiceManager.get(NavigationService.class).goTo(Page.LOGIN);
	}
	
}
