package fr.devops.game.menu;

import java.net.URISyntaxException;

import fr.devops.game.navigation.IController;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.game.network.NetworkService;
import fr.devops.shared.service.ServiceManager;
import javafx.application.Platform;
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
		errorOutput.setText("Connexion en cours...");
		new Thread(() -> {
			var net = ServiceManager.get(NetworkService.class);
			String errorString = null;
			if (!net.isConnexionAlive()) {
				try {
					net.connect(addressField.getText());
				}catch(URISyntaxException e) {
					e.printStackTrace();
					errorString = "L'adresse est incorrecte: " + e.getMessage();
				}catch(Exception e) {
					e.printStackTrace();
					errorString = "une erreur est survenue lors de la connexion: " + e.getMessage();
				}
			}
			final String errorStringFinal = errorString; // Pas très propre...
			Platform.runLater(() -> {
				if (errorStringFinal == null) {
					ServiceManager.get(NavigationService.class).goTo(Page.LOGIN);
				}else {
					errorOutput.setText(errorStringFinal);
				}
			});
		}).start();
	}
	
}
