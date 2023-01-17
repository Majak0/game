package fr.devops.game.render;

import java.util.HashMap;
import java.util.Map;

import fr.devops.game.render.entity.TestRenderer;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.service.IService;

public class EntityRendererContainer implements IService{

	private Map<EntityType, IEntityRenderer> renderers = new HashMap<>();
	
	public EntityRendererContainer() {
		registerRenderers();
	}
	
	public IEntityRenderer getRenderer(Entity entity) {
		return renderers.get(entity.getEntityType());
	}
	
	private void registerRenderers() {
		renderers.put(EntityType.TEST, new TestRenderer());
	}
	
}
