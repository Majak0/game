package fr.devops.game.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import fr.devops.game.util.WeakLinkedList;
import fr.devops.shared.ingame.event.IngameEvent;

public class NetworkService implements INetworkService {

	private final List<NetworkEventListener> listeners = new WeakLinkedList<>();

	private Socket socket;

	private Thread outThread;

	private Thread inThread;

	private boolean connexionAlive;

	private final List<Object> sendPool = new LinkedList<>();

	@Override
	public boolean connect(String address, int port) {
		try {
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
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
		}
	}

	@Override
	public synchronized void send(Object payload) {
		sendPool.add(payload);
	}

	@Override
	public void registerListener(NetworkEventListener listener) {
		listeners.add(listener);
	}

	public void unregisterListener(NetworkEventListener listener) {
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
