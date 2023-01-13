package fr.devops.game;

import fr.devops.game.ingame.IngameEventService;
import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.shared.service.ServiceManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameApplication extends Application {

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		setupServices(stage);
		ServiceManager.get(NavigationService.class).goTo(Page.INGAME);
		stage.setTitle("Le Jeu");
        stage.setFullScreen(true);
        stage.show();/*
		FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("menu/mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        fxmlLoader.getController();
        stage.setTitle("Le Jeu");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();*/
	}
	
	private void setupServices(Stage stage) {
		ServiceManager.register(new NavigationService(stage));
		ServiceManager.register(new IngameEventService());
	}

}
