package fr.devops.game.render;

import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.render.IWorldRenderer;
import fr.devops.shared.service.ServiceManager;

public class CanvasWorldRenderer implements IWorldRenderer{

	private final Canvas canvas;
	
	public CanvasWorldRenderer(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void renderWorld(IWorld world, long elapsed, int lastFPS) {
		Platform.runLater(() -> {
			var rendererContainer = ServiceManager.get(EntityRendererContainer.class);
			IEntityRenderer renderer = null;
			var context = canvas.getGraphicsContext2D();
			if (context == null) return;
			double width = canvas.getWidth(), height = canvas.getHeight();
			context.clearRect(0, 0, width, height);
			for (var entity : world.getEntities()) {
				renderer = rendererContainer.getRenderer(entity);
				renderer.render(entity, context, elapsed);
			}
			context.setFill(Color.WHITE);
			context.setTextAlign(TextAlignment.LEFT);
			context.setTextBaseline(VPos.TOP);
			context.setFont(Font.font(20));
			context.fillText(Integer.toString(lastFPS), 0, 0);
		});
	}

}
