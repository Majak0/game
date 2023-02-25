package fr.devops.game.ingame.control;

import fr.devops.game.ingame.input.PlayerInputContainer;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.service.ServiceManager;
import javafx.scene.input.KeyCode;

public class PlayerController implements IPlayerController {

	@Override
	public int getXAxis() {
		var inputs = ServiceManager.get(PlayerInputContainer.class);
		var left = inputs.isKeyPressed(KeyCode.Q) ? -1 : 0;
		var right = inputs.isKeyPressed(KeyCode.D) ? 1 : 0;
		return left + right;
	}

	@Override
	public boolean isJumping() {
		return ServiceManager.get(PlayerInputContainer.class).isKeyPressed(KeyCode.Z);
	}

}
