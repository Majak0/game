package fr.devops.server.network;

import java.net.Socket;
import java.util.Collection;

import fr.devops.shared.service.IService;

public interface IClientContainer extends IService, Collection<Client>{

	public Client add(Socket clientSocket);
	
	public Client get(int id);
	
}
