package fr.devops.shared;

public enum GameSide {
	
	CLIENT,SERVER;
	
	private static GameSide Local = CLIENT;
	
	public static void set(GameSide side) {
		Local = side;
	}
	
	public static GameSide current() {
		return Local;
	}
	
	public static boolean isServer() {
		return Local == SERVER;
	}
	
	public static boolean isClient() {
		return Local == CLIENT;
	}
	
}
