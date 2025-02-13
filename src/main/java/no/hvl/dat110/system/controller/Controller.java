package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller {

    private static int N = 5;

    public static void main(String[] args) {

        DisplayStub display;
        SensorStub sensor;

        RPCClient displayclient, sensorclient;

        System.out.println("Controller starting ...");

        displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
        sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

        RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
        RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

        display = new DisplayStub(displayclient);
        sensor = new SensorStub(sensorclient);

        displayclient.connect();
        sensorclient.connect();

        for (int i = 0; i < N; i++) {

            int sensorValue = sensor.read();

            display.write("Sensor value: " + sensorValue);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        stopdisplay.stop();
        stopsensor.stop();

        displayclient.disconnect();
        sensorclient.disconnect();

        System.out.println("Controller stopping ...");
    }
}

