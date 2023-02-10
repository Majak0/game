package fr.devops.server.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ClientContainer implements IClientContainer {

	private static int currentId = 0;

	private final Collection<Client> clients = new LinkedList<>();

	@Override
	public int size() {
		synchronized (clients) {
			removeDisconnected();
			return clients.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (clients) {
			removeDisconnected();
			return clients.isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {
		synchronized (clients) {
			removeDisconnected();
			return clients.contains(o);
		}
	}

	@Override
	public Iterator<Client> iterator() {
		synchronized (clients) {
			removeDisconnected();
			return clients.iterator();
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (clients) {
			removeDisconnected();
			return clients.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized (clients) {
			removeDisconnected();
			return clients.toArray(a);
		}
	}

	@Override
	public boolean add(Client client) {
		synchronized (clients) {
			removeDisconnected();
			return clients.add(client);
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized (clients) {
			removeDisconnected();
			if (o instanceof Client client) {
				client.disconnect();
			}
			return clients.remove(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (clients) {
			removeDisconnected();
			return clients.containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends Client> c) {
		synchronized (clients) {
			removeDisconnected();
			return clients.addAll(c);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized (clients) {
			removeDisconnected();
			var changed = false;
			var ite = clients.iterator();
			while (ite.hasNext()) {
				var item = ite.next();
				if (c.contains(item)) {
					ite.next().disconnect();
					ite.remove();
					changed = true;
				}
			}
			return changed;
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (clients) {
			removeDisconnected();
			var changed = false;
			var ite = clients.iterator();
			while (ite.hasNext()) {
				var item = ite.next();
				if (!c.contains(item)) {
					ite.next().disconnect();
					ite.remove();
					changed = true;
				}
			}
			return changed;
		}
	}

	@Override
	public void clear() {
		synchronized (clients) {
			removeDisconnected();
			var ite = clients.iterator();
			while (ite.hasNext()) {
				ite.next().disconnect();
				ite.remove();
			}
		}
	}

	@Override
	public Client add(Socket clientSocket, Consumer<Object> receiveCallback) {
		removeDisconnected();
		try {
			var oInStream = new ObjectInputStream(clientSocket.getInputStream());
			var thread = new Thread(() -> {
				try {
					while (true) {
						var received = oInStream.readObject();
						if (received != null) {
							receiveCallback.accept(received);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			var client = new Client(++currentId, clientSocket, oInStream,
					new ObjectOutputStream(clientSocket.getOutputStream()), thread);
			thread.start();
			synchronized (clients) {
				clients.add(client);
			}
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Client get(int id) {
		synchronized (clients) {
			removeDisconnected();
			for (var client : clients) {
				if (client.id() == id) {
					return client;
				}
			}
			return null;
		}
	}

	private void removeDisconnected() {
		var ite = clients.iterator();
		while (ite.hasNext()) {
			var client = ite.next();
			if (client.isDisconnected()) {
				client.disconnect();
				ite.remove();
			}
		}
	}

}
