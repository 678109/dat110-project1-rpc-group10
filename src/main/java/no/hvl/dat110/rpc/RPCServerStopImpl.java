package no.hvl.dat110.rpc;

public class RPCServerStopImpl extends RPCRemoteImpl {

	public RPCServerStopImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid, rpcserver);
	}

	public byte[] invoke(byte[] param) {
		RPCUtils.unmarshallVoid(param);
		byte[] returnval = RPCUtils.marshallVoid();
		getRpcserver().stop(); 
		return returnval;
	}
}
