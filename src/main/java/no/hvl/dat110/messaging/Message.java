package no.hvl.dat110.messaging;

import no.hvl.dat110.TODO;
import java.util.Arrays;

public class Message {

	// the up to 127 bytes of data (payload) that a message can hold
	private byte[] data;
	private static final int MAX_LOAD_SIZE = 127;

	// construction a Message with the data provided
	public Message(byte[] data) {

		//sjekker data
		if(data == null || data.length > MAX_LOAD_SIZE) {
			throw new IllegalArgumentException("Data cant be null, or more than 127 bytes long");
		}
		
		this.data = Arrays.copyOf(data, data.length);
	}

	public byte[] getData() {
		return this.data; 
	}

}
