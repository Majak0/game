package fr.devops.game;

import fr.devops.game.ingame.IngameController;
import fr.devops.game.render.CanvasRenderer;
import fr.devops.shared.ingame.GameLoop;
import fr.devops.shared.ingame.World;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {

	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		test(stage);/*
		FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("menu/mainmenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        fxmlLoader.getController();
        stage.setTitle("Le Jeu");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();*/
	}
	
	private void test(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("ingame/gamecanvas.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Le Jeu");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        if (fxmlLoader.getController() instanceof IngameController ctrl) {
        	var world = new World();
        	var renderer = new CanvasRenderer(ctrl.getCanvas());
        	var loop = new GameLoop(world, renderer);
        	new Thread(() -> loop.start()).start();
        }
	}

}
