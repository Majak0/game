package fr.devops.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("menu/mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Le Jeu");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
	}

}
