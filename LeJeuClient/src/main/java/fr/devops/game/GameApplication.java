package fr.devops.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
<<<<<<< HEAD
		setupServices(stage);
		ServiceManager.get(NavigationService.class).goTo(Page.INGAME);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		stage.setMinWidth(300);
		stage.setMinHeight(300);
		stage.setTitle("Le Jeu");
        //stage.setFullScreen(true);
=======
		FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("menu/mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Le Jeu");
        stage.setScene(scene);
        stage.setFullScreen(true);
>>>>>>> branch 'master' of https://github.com/MaximeJacquo/game
        stage.show();
	}

}
