package fr.devops.server.sync;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.devops.shared.ingame.entity.Entity;

public class EntitySyncManager implements IEntitySyncManager {

	private Map<Class<?>, Collection<Field>> fieldsStorage = new HashMap<>();
	
	@Override
	public void sendChanges(Entity entity) {
		Map<String, Object> map = new HashMap<>();
		for (var field : getFields(entity.getClass())) {
			//TODO
		}
	}
	
	private Collection<Field> getFields(Class<?> type){
		if (!fieldsStorage.containsKey(type)) {
			fieldsStorage.put(type, makeFieldList(type));
		}
		return fieldsStorage.get(type);
	}
	
	private Collection<Field> makeFieldList(Class<?> type){
		return null;
	}

}
