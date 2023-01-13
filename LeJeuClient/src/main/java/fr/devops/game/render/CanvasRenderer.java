package fr.devops.game.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.render.IRenderer;

public class CanvasRenderer implements IRenderer{

	private final Canvas canvas;
	
	public CanvasRenderer(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void renderWorld(IWorld world) {
		var context = canvas.getGraphicsContext2D();
		context.setFill(Color.BLUE);
		context.fillRect(0, 0, 200, 200);
	}

}
