package no.hvl.dat110.rpc;

public abstract class RPCLocalStub {

	protected RPCClient rpcclient;

	public RPCLocalStub(RPCClient rpcclient) {
		this.rpcclient = rpcclient;
	}

	protected byte[] call(byte rpcid, byte[] params) {
		return rpcclient.call(rpcid, params);
	}
}

