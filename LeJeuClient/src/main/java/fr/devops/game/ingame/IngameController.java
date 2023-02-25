package fr.devops.game.ingame;

import fr.devops.game.ingame.control.PlayerController;
import fr.devops.game.ingame.input.PlayerInputContainer;
import fr.devops.game.ingame.world.World;
import fr.devops.game.navigation.IController;
import fr.devops.game.render.CanvasWorldRenderer;
import fr.devops.shared.ingame.GameLoop;
import fr.devops.shared.ingame.control.IPlayerController;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.AllEntitiesRequest;
import fr.devops.shared.service.ServiceManager;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class IngameController implements IController{

	@FXML
	private Canvas canvas;
	
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public void setup() {
		var world = new World();
		var renderer = new CanvasWorldRenderer(canvas);
		ServiceManager.register(new PlayerInputContainer(canvas));
		ServiceManager.registerAs(IPlayerController.class, new PlayerController());
		var loop = new GameLoop(world, renderer);
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		new Thread(loop::start).start();
		ServiceManager.get(INetworkService.class).send(new AllEntitiesRequest());
	}
	
}
