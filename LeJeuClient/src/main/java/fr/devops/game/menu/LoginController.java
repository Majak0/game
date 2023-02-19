package fr.devops.game.menu;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.service.ServiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements IController{

	@FXML
	private TextField addressField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML Label errorOutput;
	
	@FXML
	public void onConnect() {
	}
	
	public void onRegister() {
		ServiceManager.get(NavigationService.class).goTo(Page.SIGNUP);
	}
	
	@Override
	public void setup() {
	}
	
}
