package fr.devops.game.ingame;

import fr.devops.game.ingame.world.World;
import fr.devops.game.navigation.IController;
import fr.devops.game.render.CanvasWorldRenderer;
import fr.devops.shared.ingame.GameLoop;
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
		var world = new World();
		var renderer = new CanvasWorldRenderer(canvas);
		var loop = new GameLoop(world, renderer);
		new Thread(loop::start).start();
	}
	
}
