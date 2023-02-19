package fr.devops.server;

import java.sql.SQLException;
import java.util.Scanner;

import fr.devops.server.network.ClientContainer;
import fr.devops.server.network.IClientContainer;
import fr.devops.server.network.ServerNetworkService;
import fr.devops.server.request.IRequestHandler;
import fr.devops.server.request.RequestHandler;
import fr.devops.server.sql.ISQLService;
import fr.devops.server.sql.SQLService;
import fr.devops.server.world.World;
import fr.devops.shared.GameSide;
import fr.devops.shared.ingame.GameLoop;
import fr.devops.shared.ingame.IWorld;
import fr.devops.shared.ingame.entity.EntityType;
import fr.devops.shared.ingame.event.IIngameEventService;
import fr.devops.shared.ingame.event.IngameEventService;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.service.ServiceManager;
import fr.devops.shared.sync.EntitySyncManager;
import fr.devops.shared.sync.IEntitySyncManager;
import oracle.jdbc.pool.OracleDataSource;

public class Main {

	private Scanner userInputScanner;
	
	public static void main(String[] args) {
		new Main();
	}
	
	private final ServerNetworkService network = new ServerNetworkService();
	
	public Main() {
		GameSide.set(GameSide.SERVER);
		var world = new World();
		registerServices(world);
		world.spawn(EntityType.TEST, 10, 10);
		var loop = new GameLoop(world, null);
		network.startListening(25565); // Port par défaut
		loop.start();
	}
	
	private void registerServices(IWorld world) {
		ServiceManager.registerAs(IEntitySyncManager.class, new EntitySyncManager());
		ServiceManager.registerAs(IClientContainer.class,new ClientContainer());
		ServiceManager.registerAs(INetworkService.class,network); // load before IngameEventService
		ServiceManager.registerAs(IIngameEventService.class, new IngameEventService());
		ServiceManager.registerAs(IRequestHandler.class, new RequestHandler(world));
		registerSQLService();
	}
	
	private void registerSQLService() {
		userInputScanner = new Scanner(System.in);
		var username = fetchUsername();
		var password = fetchPassword();
		userInputScanner.close();
		try {
			var source = new OracleDataSource();
			/* La base de donnée proposée par l'IUT sera utilisée. C'est une base de donnée Oracle.
			 * La seule table demandée est celle utilisée pour l'authentification, soit:
			 * Une base nommée LEJEU_CLIENT composée de trois colones:
			 * 	ID de type numérique et disposant d'un système d'auto-incrémentation (SEQUENCE SQL)
			 *  USERNAME de type varchar2 et de taille 20 charactère minimum
			 *  PASSWORD de type varchar2 et de taille 20 charactère minimum
			*/
			source.setURL("jdbc:oracle:thin:@iutdoua-ora.univ-lyon1.fr:1521:cdb1");
            source.setUser(username);
            source.setPassword(password);
            ServiceManager.registerAs(ISQLService.class, new SQLService(source));
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public String fetchUsername() {
		System.out.println("\nEntrez le nom d'utilisateur de la base de donnée (du type \"pxxxxxxx\"):\n");
		return userInputScanner.nextLine();
	}
	
	public String fetchPassword() {
		System.out.println("\nEntrez le mot de passe de la base de donnée (au dos de la carte étudiant):\n");
		return userInputScanner.nextLine();
		
	}
	
}
