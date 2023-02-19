package fr.devops.game.navigation;

public enum Page {

	CONNECT("menu/connect.fxml"),
	LOGIN("menu/login.fxml"),
	SIGNUP("menu/signup.fxml"),
	INGAME("ingame/gamecanvas.fxml");
	
	private String fxmlPath;
	
	private Page(String fxmlPath) {
		this.fxmlPath = fxmlPath;
	}
	
	public String getFxmlPath() {
		return fxmlPath;
	}
	
}
