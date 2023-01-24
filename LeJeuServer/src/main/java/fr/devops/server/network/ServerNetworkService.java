package fr.devops.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.network.INetworkEventListener;
import fr.devops.shared.network.INetworkService;

public class ServerNetworkService implements INetworkService{

	private Thread listeningThread;
	
	private boolean isListeningForNewClients;
	
	private Collection<Socket> clients = new LinkedList<>();
	
	private Collection<INetworkEventListener> listeners = new LinkedList<>();
	
	private Collection<ObjectOutputStream> clientsOut = new LinkedList<>();
	
	@Override
	public void send(Object payload) {
		synchronized(clientsOut) {
			for (var out : clientsOut) {
				try {
					out.writeObject(payload);
					out.flush();
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
		for (var clientSocket : clients) {
			try {
				clientSocket.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void registerListener(INetworkEventListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}
	
	private void addClient(Socket clientSocket) {
		try {
			var inStream = clientSocket.getInputStream();
			// Create listeningThread
			var clientInThread = new Thread(() -> {
				try {
					var oInStream = new ObjectInputStream(inStream);
					while (true) {
						var received = oInStream.readObject();
						if (received != null) {
							onPacketReceive(received);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			clientInThread.start();
			var out = new ObjectOutputStream(clientSocket.getOutputStream());
			synchronized(clientsOut) {
				clientsOut.add(out);
			}
			synchronized(clients){
				clients.add(clientSocket);
			}
			syncNewClientWorld(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void syncNewClientWorld(ObjectOutputStream out) {
	}

	private void onPacketReceive(Object payload) {
		synchronized (listeners) {
			if (payload instanceof IngameEvent event) {
				for (var listener : listeners) {
					listener.onNetworkIngameEvent(event);
				}
			}
		}
	}

}
