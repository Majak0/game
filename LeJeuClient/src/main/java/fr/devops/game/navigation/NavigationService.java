package fr.devops.game.navigation;

import fr.devops.game.GameApplication;
import fr.devops.shared.service.IService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationService implements IService{

	private final Stage stage;
	
	public NavigationService(Stage stage) {
		this.stage = stage;
	}
	
	public void goTo(Page page) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(page.getFxmlPath()));
	        Scene scene = new Scene(fxmlLoader.load(), 300, 300);
	        stage.setScene(scene);
	        if (fxmlLoader.getController() instanceof IController controller) {
	        	controller.setup();
	        }
	        stage.show();
		}catch(Exception e) {
			e.printStackTrace();  // TODO Faire du bon logging
		}
	}
	
}
