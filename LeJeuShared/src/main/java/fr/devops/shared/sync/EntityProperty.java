package fr.devops.shared.sync;

import java.io.Serializable;

public class EntityProperty<T extends Serializable> {

	private Class<T> type;

	@SuppressWarnings("unchecked")
	public EntityProperty(T defaultValue) {
		if (defaultValue == null) {
			throw new RuntimeException("Impossible de donner la valeur null par défaut à une propriété syncrhonisée.");
		}
		this.type = (Class<T>) defaultValue.getClass();
		value = defaultValue;
		oldValue = value;
	}

	private T value;

	private T oldValue;

	public T getValue() {
		return oldValue;
	}
	
	public T getChangedValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean changed() {
		if (value != null) {
			return !value.equals(oldValue);
		}
		if (oldValue != null) {
			return !oldValue.equals(value);
		}
		return false;
	}

	public void update() {
		oldValue = value;
	}

	public void setCastValue(Object object) {
		if (type.isInstance(object)) {
			setValue(type.cast(object));
		}
	}

}