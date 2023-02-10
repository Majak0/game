package fr.devops.server.network;

import java.net.Socket;
import java.util.Collection;
import java.util.function.Consumer;

import fr.devops.shared.service.IService;

public interface IClientContainer extends IService, Collection<Client>{
	
	public Client get(int id);

	public Client add(Socket clientSocket, Consumer<Object> receiveCallback);
	
}
