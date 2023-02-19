package fr.devops.game.ingame.input;

import fr.devops.shared.ingame.IWorld;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class PlayerInputDispatcher implements EventHandler<MouseEvent>{

	public PlayerInputDispatcher(Canvas canvas, IWorld world) {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
	}

	@Override
	public void handle(MouseEvent event) {
		switch (event.getButton()) {
		case BACK:
			break;
		case FORWARD:
			break;
		case MIDDLE:
			break;
		case NONE:
			break;
		case PRIMARY:
			break;
		case SECONDARY:
			break;
		default:
			break;
		}
	}
	
}
