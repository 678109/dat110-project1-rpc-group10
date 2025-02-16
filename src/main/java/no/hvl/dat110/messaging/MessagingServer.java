package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {

	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException ex) {

			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	public MessageConnection accept() {

		MessageConnection connection = null;

		try {
			System.out.println("Waiting for a client to connect");
			Socket clientSocket = welcomeSocket.accept();

			connection = new MessageConnection(clientSocket);
			System.out.println("Client connected");

		} catch (IOException ex) {
			System.out.println("Error accepting connection " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return connection;

	}

	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException ex) {

				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
