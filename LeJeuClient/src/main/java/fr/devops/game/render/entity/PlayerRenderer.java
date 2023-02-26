package fr.devops.game.render.entity;

import fr.devops.game.render.IEntityRenderer;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityPlayer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlayerRenderer implements IEntityRenderer {

	@Override
	public void render(Entity entity, GraphicsContext context, double elapsedTime) {
		if (entity instanceof EntityPlayer player) {
			context.setFill(Color.RED);
			context.fillRect(entity.getX(), entity.getY(), EntityPlayer.size, EntityPlayer.size);
		}
	}
}
