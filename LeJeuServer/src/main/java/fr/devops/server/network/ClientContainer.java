package fr.devops.server.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientContainer implements IClientContainer {

	private static int currentId = 0;
	
	private final Collection<Client> clients = new LinkedList<>();
	
	@Override
	public int size() {
		return clients.size();
	}

	@Override
	public boolean isEmpty() {
		return clients.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return clients.contains(o);
	}

	@Override
	public Iterator<Client> iterator() {
		return clients.iterator();
	}

	@Override
	public Object[] toArray() {
		return clients.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return clients.toArray(a);
	}

	@Override
	public boolean add(Client client) {
		return clients.add(client);
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof Client client) {
			client.disconnect();
		}
		return clients.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return clients.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Client> c) {
		return clients.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
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

	@Override
	public boolean retainAll(Collection<?> c) {
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

	@Override
	public void clear() {
		var ite = clients.iterator();
		while (ite.hasNext()) {
			ite.next().disconnect();
			ite.remove();
		}
	}

	@Override
	public Client add(Socket clientSocket) {
		try {
			var client =  new Client(++currentId, clientSocket, new ObjectInputStream(clientSocket.getInputStream()), new ObjectOutputStream(clientSocket.getOutputStream()));
			clients.add(client);
			return client;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public Client get(int id) {
		for (var client : clients) {
			if (client.id() == id) {
				return client;
			}
		}
		return null;
	}

}
