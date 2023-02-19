package fr.devops.game.navigation;

import fr.devops.game.GameApplication;
import fr.devops.shared.service.IService;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationService implements IService{
	
	public static final String AUTHENTICATION_FAILURE_MESSAGE = "AuthenticationFailureMessage";

	private final Stage stage;
	
	private Page currentPage;
	
	private IController currentController;
	
	public NavigationService(Stage stage) {
		this.stage = stage;
	}
	
	public void goTo(Page page) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource(page.getFxmlPath()));
	        Scene scene = new Scene(fxmlLoader.load());
	        stage.setScene(scene);
	        if (fxmlLoader.getController() instanceof IController controller) {
	        	controller.setup();
	        	currentController = controller;
	        }
	        currentPage = page;
	        stage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void UiThreadGoTo(Page page) {
		Platform.runLater(() -> goTo(page));
	}
	
	public Page getCurrentPage() {
		return currentPage;
	}
	
	public IController getCurrentController() {
		return currentController;
	}
	
	public void sendDataToController(String key, Object value) {
		if (currentController != null) {
			currentController.receiveData(key, value);
		}
	}
	
}
