package no.hvl.dat110.rpc;

public abstract class RPCRemoteImpl {
	
	private byte rpcid;
	private RPCServer rpcserver;

	public RPCRemoteImpl(byte rpcid, RPCServer rpcserver) {
		this.rpcid = rpcid;
		this.rpcserver = rpcserver;
		rpcserver.register(rpcid, this);
	}

	public byte getRpcid() {
		return rpcid;
	}

	public RPCServer getRpcserver() {
		return rpcserver;
	}

	public abstract byte[] invoke(byte[] params);
}
