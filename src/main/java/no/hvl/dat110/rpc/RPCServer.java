package no.hvl.dat110.rpc;

import java.util.HashMap;
import no.hvl.dat110.messaging.*;

public class RPCServer {

    private MessagingServer msgserver;
    private MessageConnection connection;
    private HashMap<Byte, RPCRemoteImpl> services;

    public RPCServer(int port) {
        this.msgserver = new MessagingServer(port);
        this.services = new HashMap<>();
    }

    public void run() {
        RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
        services.put(RPCCommon.RPIDSTOP, rpcstop);

        System.out.println("RPC SERVER RUN - Services: " + services.size());
        connection = msgserver.accept();

        if (connection == null) {
            throw new RuntimeException("Failed to accept client connection.");
        }

        System.out.println("RPC SERVER ACCEPTED");

        boolean stop = false;

        while (!stop) {
            try {
                System.out.println("RPCServer: Waiting for request...");
                Message requestmsg = connection.receive();

                if (requestmsg == null) {
                    System.out.println("RPCServer: Received null message. Client may have disconnected.");
                    break;
                }

                byte[] requestData = requestmsg.getData();
                if (requestData.length == 0) {
                    System.out.println("RPCServer: Received empty request data.");
                    continue;
                }

                byte rpcid = requestData[0];
                System.out.println("RPCServer: Processing RPC ID: " + rpcid);

                byte[] param = RPCUtils.decapsulate(requestData);
                RPCRemoteImpl method = services.get(rpcid);

                byte[] returnValue = (method != null) ? method.invoke(param) : new byte[0];

                byte[] replyData = RPCUtils.encapsulate(rpcid, returnValue);
                Message replymsg = new Message(replyData);

                if (rpcid == RPCCommon.RPIDSTOP) {
                    System.out.println("RPCServer: Stop command received. Sending response before shutdown.");
                    connection.send(replymsg);
                    stop = true; // Setter stop først for å unngå at vi prøver å sende etter at vi har lukket
                    break;
                } else {
                    System.out.println("RPCServer: Sending response for RPC ID: " + rpcid);
                    connection.send(replymsg);
                }
            } catch (Exception e) {
                System.out.println("RPCServer: Exception in loop.");
                e.printStackTrace();
                break;
            }
        }

        System.out.println("RPCServer: Closing connection...");
        connection.close();
        System.out.println("RPCServer: Stopping server...");
        stop();
    }

    public MessageConnection accept() {
        return msgserver.accept();
    }

    public void register(byte rpcid, RPCRemoteImpl impl) {
        services.put(rpcid, impl);
    }

    public void stop() {
        if (connection != null) {
            connection.close();
        } else {
            System.out.println("RPCServer.stop - connection was null");
        }
        if (msgserver != null) {
            msgserver.stop();
        } else {
            System.out.println("RPCServer.stop - msgserver was null");
        }
    }
}
