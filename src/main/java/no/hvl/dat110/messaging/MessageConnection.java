package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLOutput;

import no.hvl.dat110.TODO;


public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {
try {
	byte[] data = MessageUtils.encapsulate(message);

	// TODO - START
	// encapsulate the data contained in the Message and write to the output stream

	outStream.write(data);
	outStream.flush();
} catch (IOException ex) {
	System.out.println("Error, cant send message: " + ex.getMessage());
	ex.printStackTrace();
}
		// TODO - END
	}

	public Message receive() {

		Message message = null;

		try {
			byte[] segment = new byte[MessageUtils.SEGMENTSIZE];
			inStream.readFully(segment);

			message = MessageUtils.decapsulate(segment);
		} catch(IOException ex) {
			System.out.println("Error, cant recieve message " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return message;
		
	}
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}