package pinger.model;

import pinger.view.MainView;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Pinger {
    private int timeout = 500;

    private ArrayList<Host> hosts;

    public Pinger(ArrayList<Host> hosts) {
        this.hosts = hosts;
    }

    public static void checkHosts(MainView view, ArrayList<Host> hosts) throws Exception {
        for (Host host : hosts) {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host.getUri(), host.getPort()), 1000);
                if (!host.isAvailable()) {
                    host.setAvailable(true);
                    JsonWriter.updateHost(host);
                    view.reload();
                }
            } catch (IOException e) {
                if (host.isAvailable()) {
                    host.setAvailable(false);
                    JsonWriter.updateHost(host);
                    view.reload();
                }
            }
        }
    }
}
