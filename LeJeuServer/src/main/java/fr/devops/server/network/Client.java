package fr.devops.server.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public record Client(int id, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
	
	public void send(Object payload) {
		try {
			System.out.println("send");
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
			e.printStackTrace();
		}
	}
	
}
