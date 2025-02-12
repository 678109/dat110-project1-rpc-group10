package no.hvl.dat110.rpc;

public class RPCClientStopStub extends RPCLocalStub {

	public RPCClientStopStub(RPCClient rpcclient) {
		super(rpcclient);
	}

	public void stop() {
	    byte[] request = RPCUtils.marshallVoid();
	    byte[] response = rpcclient.call(RPCCommon.RPIDSTOP, request);

	    if (response == null) {
	        System.out.println("RPCClientStopStub: No response from server, stopping anyway.");
	        return;
	    }

	    RPCUtils.unmarshallVoid(response);
	}


}