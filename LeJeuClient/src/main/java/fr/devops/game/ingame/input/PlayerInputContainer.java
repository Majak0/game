package fr.devops.game.ingame.input;

import java.util.HashMap;
import java.util.Map;

import fr.devops.shared.service.IService;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerInputContainer implements IService, EventHandler<KeyEvent>{

	private Map<KeyCode,Boolean> keyboardKeys = new HashMap<>();
	
	public PlayerInputContainer(Canvas canvas) {
		canvas.setOnKeyPressed(this);
		canvas.setOnKeyReleased(this);
	}
	
	public synchronized boolean isKeyPressed(KeyCode key) {
		if (keyboardKeys.containsKey(key)) {
			return keyboardKeys.get(key);
		}
		return false;
	}

	private synchronized void setKeyStatus(KeyCode key, boolean status) {
		keyboardKeys.put(key, status);
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
			setKeyStatus(event.getCode(), true);
		}else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
			setKeyStatus(event.getCode(), false);
		}
	}
}
