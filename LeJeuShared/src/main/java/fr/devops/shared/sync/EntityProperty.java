package fr.devops.shared.sync;

public class EntityProperty<T> {
	
	public EntityProperty(T defaultValue) {
		value = defaultValue;
		oldValue = value;
	}
	
	private T value;
	
	private T oldValue;
	
	public T getValue() {
		return oldValue;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public boolean changed() {
		if (value != null) {
			return value.equals(oldValue);
		}
		if (oldValue != null) {
			return oldValue.equals(value);
		}
		return false;
	}
	
	public void update() {
		oldValue = value;
	}
	
}