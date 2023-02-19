package fr.devops.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public record Client(int id, Socket socket, ObjectInputStream in, ObjectOutputStream out, Thread inThread) {
	
	public void send(Object payload) {
		try {
			out.writeObject(payload);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			in.close();
			out.close();
			socket.close();
		}catch(Exception e) {
		}
	}
	
	public boolean isDisconnected() {
		return inThread != null ? !inThread.isAlive() : true;
	}
	
}
