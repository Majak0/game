package fr.devops.game.menu;

import fr.devops.game.navigation.IController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainMenuController implements IController{

	@FXML
	private TextField addressField;
	
	@FXML
	public void onConnect() {
	}
	
	@Override
	public void setup() {
	}
	
}
