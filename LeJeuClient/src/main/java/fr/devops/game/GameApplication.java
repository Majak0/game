package fr.devops.game;

import fr.devops.game.navigation.NavigationService;
import fr.devops.game.navigation.Page;
import fr.devops.game.network.NetworkService;
import fr.devops.game.render.EntityRendererContainer;
import fr.devops.game.response.IResponseHandler;
import fr.devops.game.response.ResponseHandler;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntitySyncManager;
import fr.devops.shared.sync.IEntitySyncManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class GameApplication extends Application {
	
	@Override
	public void start(@SuppressWarnings("exports") Stage stage) throws Exception {
		System.out.println("Client start");
		setupServices(stage);	
		//ServiceManager.get(NetworkService.class).connect("127.0.0.1", 25565);
		ServiceManager.get(NavigationService.class).goTo(Page.CONNECT);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		stage.setMinWidth(1280);
		stage.setMinHeight(720);
		stage.setTitle("Le Jeu");
        //stage.setFullScreen(true);
        stage.show();
	}

	private void setupServices(Stage stage) {
		ServiceManager.registerAs(IResponseHandler.class, new ResponseHandler());
		ServiceManager.registerAs(IEntitySyncManager.class, new EntitySyncManager());
		ServiceManager.register(new NavigationService(stage));
		ServiceManager.register(new NetworkService()); // allow us to access INetworkService's client implementation
		ServiceManager.registerAs(INetworkService.class,ServiceManager.get(NetworkService.class));
		ServiceManager.register(new EntityRendererContainer());
	}

}
