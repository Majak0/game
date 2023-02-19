package fr.devops.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import fr.devops.server.request.IRequestHandler;
import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.network.INetworkEventListener;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.request.IRequest;
import fr.devops.shared.service.ServiceManager;

public class ServerNetworkService implements INetworkService{

	private Thread listeningThread;
	
	private boolean isListeningForNewClients;
	
	private Collection<INetworkEventListener> listeners = new LinkedList<>();
	
	@Override
	public void send(Object payload) {
		var container = ServiceManager.get(IClientContainer.class);
		synchronized(container) {
			for (var client : container) {
				try {
					client.send(payload);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean startListening(int port) {
		if (listeningThread != null) {
			disconnectAll();
		}
		try {
			listeningThread = new Thread(() -> {
				
				// Listening for new client thread
				try (var socket = new ServerSocket(port);) {
					var listening = false;
					synchronized(listeningThread) {
						listening = isListeningForNewClients;
					}
					while (listening) {
						addClient(socket.accept());
						synchronized(listeningThread) {
							listening = isListeningForNewClients;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				//
				
			});
			listeningThread.start();
			synchronized(listeningThread){
				isListeningForNewClients = true;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void disconnectAll() {
		try {
			if (listeningThread != null) {
				synchronized(listeningThread) {
					isListeningForNewClients = false;
				}
				listeningThread.join();
			}
			listeningThread = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServiceManager.get(IClientContainer.class).clear();
	}

	@Override
	public void registerListener(INetworkEventListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}
	
	private void addClient(Socket clientSocket) {
		try {
			var container = ServiceManager.get(IClientContainer.class);
			Client client = null;
			synchronized(container) {
				client = container.add(clientSocket, this::onPacketReceive);
			}
			if (client == null) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onPacketReceive(int clientId, Object payload) {
		synchronized (listeners) {
			if (payload instanceof IngameEvent event) {
				for (var listener : listeners) {
					listener.onNetworkIngameEvent(event);
				}
			}else if (payload instanceof IRequest request) {
				ServiceManager.get(IRequestHandler.class).handleRequest(clientId, request);
			}
		}
	}

}
