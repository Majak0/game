package fr.devops.game.menu;

import java.net.URISyntaxException;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.game.network.NetworkService;
import fr.devops.shared.service.ServiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnectController implements IController{

	@FXML
	private TextField addressField;
	
	@FXML
	private Label errorOutput;
	
	@FXML
	public void onConnect() {
		System.out.println("try connecting");
		var net = ServiceManager.get(NetworkService.class);
		if (!net.isConnexionAlive()) {
			try {
				net.connect(addressField.getText());
				ServiceManager.get(NavigationService.class).goTo(Page.LOGIN);
			}catch(URISyntaxException e) {
				errorOutput.setText("L'adresse est incorrecte: " + e.getMessage());
			}catch(Exception e) {
				errorOutput.setText("une erreur est survenue lors de la connexion: " + e.getMessage());
			}
		}
	}
	
	@Override
	public void setup() {
	}
	
}
