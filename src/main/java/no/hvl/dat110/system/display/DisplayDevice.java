package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;

public class DisplayDevice {

    public static void main(String[] args) {

        System.out.println("Display server starting ...");

        RPCServer rpcserver = new RPCServer(Common.DISPLAYPORT);

        DisplayImpl displayImpl = new DisplayImpl((byte) Common.WRITE_RPCID, rpcserver); 

        rpcserver.register((byte) Common.WRITE_RPCID, displayImpl);  

        rpcserver.run();

        System.out.println("Display server stopping ...");
    }
}
