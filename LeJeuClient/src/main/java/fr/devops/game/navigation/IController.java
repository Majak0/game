package fr.devops.game.navigation;

public interface IController {

	public default void setup() {};
	
	public default void receiveData(String key, Object value) {};
	
}
