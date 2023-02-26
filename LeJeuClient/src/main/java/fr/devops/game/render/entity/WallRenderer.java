package fr.devops.game.render.entity;

import fr.devops.game.render.IEntityRenderer;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityWall;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WallRenderer implements IEntityRenderer {

	@Override
	public void render(Entity entity, GraphicsContext context, double elapsedTime) {
		if (entity instanceof EntityWall wall) {
			context.setFill(Color.GRAY);
			context.fillRect(entity.getX(), entity.getY(), EntityWall.size, EntityWall.size);
		}
	}
}
