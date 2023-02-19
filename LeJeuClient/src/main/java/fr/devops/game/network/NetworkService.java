package fr.devops.game.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import fr.devops.game.response.IResponseHandler;
import fr.devops.shared.ingame.event.IngameEvent;
import fr.devops.shared.network.INetworkEventListener;
import fr.devops.shared.network.INetworkService;
import fr.devops.shared.network.response.IResponse;
import fr.devops.shared.service.ServiceManager;

public class NetworkService implements INetworkService {

	private final List<INetworkEventListener> listeners = new LinkedList<>();

	private Socket socket;

	private Thread outThread;

	private Thread inThread;

	private boolean connexionAlive;
	
	private int clientId = -1;

	private final List<Object> sendPool = new LinkedList<>();

	public void connect(String uri) throws URISyntaxException, IOException {
		connect(new URI(uri));
	}

	public void connect(URI uri) throws IOException {
		connect(uri.getHost(), uri.getPort());
	}

	public void connect(String address, int port) throws IOException {
		// Problème de parse URI, le port n'est pas détecté
		System.out.println("Tentative de connection a l'adresse " + address + " au port " + port);
		if (port < 0) {
			port = 25565; // Port par défaut
		}
		if (isConnexionAlive()) {
			disconnect();
		}
		setSocket(new Socket(address, port));
		var outStream = new ObjectOutputStream(getSocket().getOutputStream());
		var inStream = new ObjectInputStream(getSocket().getInputStream());
		outThread = new Thread(() -> {
			while (isConnexionAlive()) {
				try {
					synchronized (sendPool) {
						for (var payload : sendPool) {
							outStream.writeObject(payload);
						}
						sendPool.clear();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		inThread = new Thread(() -> {
			while (isConnexionAlive()) {
				try {
					onReceived(inStream.readObject());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		setConnexionAlive(true);
		outThread.start();
		inThread.start();
	}

	public void disconnect() {
		setConnexionAlive(false);
		try {
			if (outThread != null) {
				outThread.join();
			}
			if (inThread != null) {
				inThread.join();
			}
			if (getSocket() != null) {
				socket.shutdownOutput();
				socket.shutdownInput();
				setSocket(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void onReceived(Object payload) {
		if (payload instanceof IngameEvent evt) {
			for (var listener : listeners) {
				listener.onNetworkIngameEvent(evt);
			}
		}else if (payload instanceof IResponse response) {
			ServiceManager.get(IResponseHandler.class).handleResponse(response);
		}
	}

	@Override
	public synchronized void send(Object payload) {
		synchronized (sendPool) {
			sendPool.add(payload);
		}
	}
	
	public void setClientId(int id) {
		clientId = id;
	}

	public int getClientId() {
		return clientId;
	}
	
	@Override
	public void registerListener(INetworkEventListener listener) {
		listeners.add(listener);
	}

	public void unregisterListener(INetworkEventListener listener) {
		listeners.remove(listener);
	}

	public synchronized boolean isConnexionAlive() {
		return connexionAlive;
	}

	private synchronized void setConnexionAlive(boolean alive) {
		connexionAlive = alive;
	}

	public synchronized Socket getSocket() {
		return socket;
	}

	public synchronized void setSocket(Socket socket) {
		this.socket = socket;
	}

}
