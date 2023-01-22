package fr.devops.shared.ingame.entity;

import java.util.function.Supplier;

public enum EntityType {

	TEST(EntityTest::new);
	
	private final Supplier<Entity> constructor;
	
	private EntityType(Supplier<Entity> constructor) {
		this.constructor = constructor;
	}
	
	public Entity makeNew() {
		return constructor.get();
	}
	
}
