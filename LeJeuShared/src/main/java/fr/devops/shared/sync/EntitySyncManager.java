package fr.devops.shared.sync;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import fr.devops.shared.GameSide;
import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.event.EntityCreatedEvent;
import fr.devops.shared.ingame.event.EntityModifiedEvent;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;

public class EntitySyncManager implements IEntitySyncManager {

	private final Map<Class<?>, Collection<Property>> fieldsStorage = new HashMap<>();

	@Override
	public void sendChanges(Entity entity) {
		Map<String, Object> map = new HashMap<>();
		for (var property : getFields(entity.getClass())) {
			try {
				if (property.field.get(entity) instanceof EntityProperty<?> entityProperty) {
					if (entityProperty.changed()) {
						entityProperty.update();
						map.put(property.name, entityProperty.getValue());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.size() > 0) {
			ServiceManager.get(INetworkService.class).send(new EntityModifiedEvent(entity.getId(), map));
		}
	}

	@Override
	public void sendCreation(Entity entity) {
		Map<String, Object> map = new HashMap<>();
		for (var property : getFields(entity.getClass())) {
			try {
				if (property.field.get(entity) instanceof EntityProperty<?> entityProperty) {
					entityProperty.update();
					map.put(property.name, entityProperty.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServiceManager.get(INetworkService.class).send(new EntityCreatedEvent(entity));
		if (map.size() > 0) {
			ServiceManager.get(INetworkService.class).send(new EntityModifiedEvent(entity.getId(), map));
		}
	}

	@Override
	public void applyChanges(Entity entity, Map<String, Object> map) {
		try {
			for (var prop : getFields(entity.getClass())) {
				if (map.containsKey(prop.name)) {
					if (prop.field.get(entity) instanceof EntityProperty<?> entityProperty) {
						entityProperty.setCastValue(map.get(prop.name));
						if (GameSide.isClient()) {
							entityProperty.update();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Collection<Property> getFields(Class<?> type) {
		if (!fieldsStorage.containsKey(type)) {
			fieldsStorage.put(type, makeFieldList(type));
		}
		return fieldsStorage.get(type);
	}

	private Collection<Property> makeFieldList(Class<?> type) {
		System.out.println("Replicated fields for type "+type.getName() + ':');
		Collection<Property> result = new LinkedList<>();
		for (var field : type.getDeclaredFields()) {
			if (field.isAnnotationPresent(SyncEntityProperty.class) && field.trySetAccessible()
					&& EntityProperty.class.isAssignableFrom(field.getType())) {
				System.out.println("- " + field.getName());
				result.add(new Property(field));
			}
		}
		var superclass = type.getSuperclass();
		if (superclass == null) {
			return result;
		}
		result.addAll(makeFieldList(superclass)); // Tail recursivity?
		return result;
	}

	private class Property {

		private Property(Field field) {
			this.field = field;
			name = field.getName();
		}

		private String name;
		private Field field;

	}

}
