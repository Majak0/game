package fr.devops.game.ingame;

import fr.devops.game.navigation.IController;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class IngameController implements IController{

	@FXML
	private Canvas canvas;
	
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void setup() {
	}
	
}
