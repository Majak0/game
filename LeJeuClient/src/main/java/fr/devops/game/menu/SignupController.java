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
	private Label errorOutput;
	
	@FXML
	public void onRegister() {
	}
	
	@Override
	public void setup() {
	}
	
	public void onNavigateBack() {
		ServiceManager.get(NavigationService.class).goTo(Page.LOGIN);
	}
	
}
