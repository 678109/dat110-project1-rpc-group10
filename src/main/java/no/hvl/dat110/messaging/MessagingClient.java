package no.hvl.dat110.messaging;


import java.net.Socket;

public class MessagingClient {

	private String server;

	private int port;
	
	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public MessageConnection connect () {
		MessageConnection connection = null;

		try {

		Socket clientSocket = new Socket(server, port);

		connection = new MessageConnection(clientSocket);

	} catch (Exception e) {
				System.out.println("Failed connection to server " + e.getMessage());
				e.printStackTrace();
			}
			return connection;
		}
}

