package fr.devops.game;

import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.game.network.NetworkService;
import fr.devops.game.render.EntityRendererContainer;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GameApplication extends Application {
	
	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		setupServices(stage);
		ServiceManager.get(NavigationService.class).goTo(Page.INGAME);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		stage.setMinWidth(300);
		stage.setMinHeight(300);
		stage.setTitle("Le Jeu");
        //stage.setFullScreen(true);
        stage.show();
	}

	private void setupServices(Stage stage) {
		ServiceManager.register(new NavigationService(stage));
		ServiceManager.registerAs(INetworkService.class,new NetworkService()); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		ServiceManager.register(new EntityRendererContainer());
	}

}
