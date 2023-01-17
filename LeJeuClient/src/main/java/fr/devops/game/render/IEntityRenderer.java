package fr.devops.game.render;

import fr.devops.shared.ingame.entity.Entity;
import javafx.scene.canvas.GraphicsContext;

public interface IEntityRenderer {

	public void render(Entity entity, GraphicsContext context, double elapsedTime);
	
}
