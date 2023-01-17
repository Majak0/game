package fr.devops.game.render.entity;

import fr.devops.game.render.IEntityRenderer;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityTest;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TestRenderer implements IEntityRenderer {

	@Override
	public void render(Entity entity, GraphicsContext context, double elapsedTime) {
		if (entity instanceof EntityTest test) {
			context.setFill(Color.BLUE);
			context.fillRect(entity.getX(), entity.getY(), test.getSize(), test.getSize());
		}
	}

}
