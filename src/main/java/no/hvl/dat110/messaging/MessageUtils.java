package no.hvl.dat110.messaging;

import java.util.Arrays;

import no.hvl.dat110.TODO;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data = message.getData();

		if(data.length > 127) {
			throw new IllegalArgumentException("Message is to long");
		}
		
		segment[0] = (byte) data.length;

		System.arraycopy(data, 0, segment, 1, data.length);

		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		Message message = null;
		
		int length = segment[0];

		byte[] data = Arrays.copyOfRange(segment, 1, 1 + length);
		
		return new Message(data);
		
	}
	
}
