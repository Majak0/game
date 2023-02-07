package fr.devops.server.sync;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import fr.devops.shared.ingame.entity.Entity;
import fr.devops.shared.ingame.event.EntityModifiedEvent;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntityProperty;
import fr.devops.shared.sync.SyncEntityProperty;

public class EntitySyncManager implements IEntitySyncManager {

	private Map<Class<?>, Collection<Property>> fieldsStorage = new HashMap<>();
	
	@Override
	public void sendChanges(Entity entity) {
		Map<String, Object> map = new HashMap<>();
		for (var property : getFields(entity.getClass())) {
			try {
				if (property.field.get(entity) instanceof EntityProperty<?> entityProperty && entityProperty.changed()) {
					map.put(property.name, entityProperty.getValue());
					entityProperty.update();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (map.size() > 0) {
			ServiceManager.get(INetworkService.class).send(new EntityModifiedEvent(entity.getId(), map));
		}
	}
	
	private Collection<Property> getFields(Class<?> type){
		if (!fieldsStorage.containsKey(type)) {
			fieldsStorage.put(type, makeFieldList(type));
		}
		return fieldsStorage.get(type);
	}
	
	private Collection<Property> makeFieldList(Class<?> type){
		Collection<Property> result = new LinkedList<>();
		for (var field : type.getFields()) {
			if (field.isAnnotationPresent(SyncEntityProperty.class) && field.trySetAccessible() && EntityProperty.class.isAssignableFrom(field.getType())) {
				result.add(new Property(field));
			}
		}
		return result;
	}
	
	private class Property{
		
		private Property(Field field) {
			this.field = field;
			name = field.getName();
		}
		private String name;
		private Field field;
		
	}

}
