package no.hvl.dat110.messaging;


import java.net.Socket;

public class MessagingClient {

	// name/IP address of the messaging server
	private String server;

	// server port on which the messaging server is listening
	private int port;
	
	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	// setup of a messaging connection to a messaging server
	public MessageConnection connect () {
		MessageConnection connection = null;

		try {
		// client-side socket for underlying TCP connection to messaging server
		Socket clientSocket = new Socket(server, port);

		connection = new MessageConnection(clientSocket);

	} catch (Exception e) {
				System.out.println("Failed connection to server " + e.getMessage());
				e.printStackTrace();
			}
			return connection;
		}
}

