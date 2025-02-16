package no.hvl.dat110.messaging;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


public class MessageConnection {

	private DataOutputStream outStream; 
	private DataInputStream inStream; 
	private Socket socket;
	
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
	
	public Socket getSocket() {
		return socket;
	}

	public void send(Message message) {
	    try {
	        if (isClosed()) {
	            System.out.println("MessageConnection: Cannot send message, socket is closed.");
	            return;
	        }

	        byte[] data = MessageUtils.encapsulate(message);
	        outStream.write(data);
	        outStream.flush();

	        System.out.println("MessageConnection: Sent message of " + data.length + " bytes");
	    } catch (IOException ex) {
	        System.out.println("MessageConnection: Error sending message: " + ex.getMessage());
	        ex.printStackTrace();
	    }
	}


	public Message receive() {
	    Message message = null;

	    try {
	        if (isClosed()) {
	            System.out.println("MessageConnection: Cannot receive message, socket is closed.");
	            return null;
	        }

	        byte[] segment = new byte[MessageUtils.SEGMENTSIZE];

	        int bytesRead = inStream.read(segment);
	        if (bytesRead == -1) {
	            System.out.println("MessageConnection: Received EOF, connection closed by peer.");
	            return null;
	        }

	        System.out.println("MessageConnection: Received " + bytesRead + " bytes.");
	        message = MessageUtils.decapsulate(segment);
	    } catch (EOFException ex) {
	        System.out.println("MessageConnection: EOFException - Connection closed.");
	        return null;
	    } catch (IOException ex) {
	        System.out.println("MessageConnection: IOException while receiving: " + ex.getMessage());
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
	
	public boolean isClosed() {
	    return socket == null || socket.isClosed();
	}

}