package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.*;

public class RPCClient {

    private MessagingClient msgclient;
    private MessageConnection connection;

    public RPCClient(String server, int port) {
        this.msgclient = new MessagingClient(server, port);
    }

    public void connect() {
        if (connection == null) {
            connection = msgclient.connect();
        }
    }

    public void disconnect() {
        if (connection != null && !connection.isClosed()) {
            System.out.println("RPCClient: Disconnecting...");
            connection.close();
            connection = null;
        }
    }

    public byte[] call(byte rpcid, byte[] param) {
        byte[] request = RPCUtils.encapsulate(rpcid, param);

        System.out.println("RPCClient: Sending request with RPC ID " + rpcid);
        connection.send(new Message(request));

        Message response = connection.receive();

        if (response == null) {
            System.out.println("RPCClient: No response received. Server might have closed connection.");
            return new byte[0];
        }

        System.out.println("RPCClient: Received response for RPC ID " + rpcid);
        return RPCUtils.decapsulate(response.getData());
    }



}
