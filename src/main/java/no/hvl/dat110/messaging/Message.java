package no.hvl.dat110.messaging;

import java.util.Arrays;

public class Message {

	private byte[] data;
	private static final int MAX_LOAD_SIZE = 127;

	public Message(byte[] data) {

		if(data == null || data.length > MAX_LOAD_SIZE) {
			throw new IllegalArgumentException("Data can't be null, or more than 127 bytes long");
		}
		
		this.data = Arrays.copyOf(data, data.length);
	}

	public byte[] getData() {
		return this.data; 
	}

}
