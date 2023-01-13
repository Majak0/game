package fr.devops.game.render;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.render.IRenderer;

public class CanvasRenderer implements IRenderer{

	private final Canvas canvas;
	
	private int test = 0;
	
	public CanvasRenderer(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void renderWorld(IWorld world, long elapsed, int lastFPS) {
		Platform.runLater(() -> {
			var context = canvas.getGraphicsContext2D();
			if (context == null) return;
			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			context.setFill(Color.WHITE);
			context.fillRect(test++, 300, 100, 100);
			context.setTextAlign(TextAlignment.LEFT);
			context.setTextBaseline(VPos.TOP);
			context.setFont(Font.font(20));
			context.fillText(Integer.toString(lastFPS), 0, 0);
			if (test > 300) test=0;
		});
	}

}
