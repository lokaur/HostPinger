package pinger.controller;

import pinger.model.Host;
import pinger.model.JsonWriter;
import pinger.model.Pinger;
import pinger.view.MainView;

import java.util.ArrayList;

public class PingerController {
    private MainView mainFrame;

    private ArrayList<Host> hosts;

    private boolean isPinging;

    public PingerController() {
        try {
            setHostsUnavailable();
            mainFrame = new MainView(this);
            this.hosts = JsonWriter.loadHostsList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void reloadTable() {
        try {
            hosts = JsonWriter.loadHostsList();
            mainFrame.reload();
        } catch (Exception ignored) {}
    }

    public void startHostPinger() {
        isPinging = true;
        Thread pingerThread = new Thread(() -> {
            while (isPinging) {
                try {
                    Pinger.checkHosts(mainFrame, hosts);
                    Thread.sleep(3000);
                } catch (Exception ignored) {
                }
            }
        });

        pingerThread.start();
    }

    public void setHostsUnavailable() {
        try {
            this.hosts = JsonWriter.loadHostsList();
            for (Host host : hosts) {
                host.setAvailable(false);
                JsonWriter.updateHost(host);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPingerThread() {
        isPinging = false;
    }

    public MainView getMainFrame() {
        return mainFrame;
    }

    public ArrayList<Host> getHosts() {
        try {
            hosts = JsonWriter.loadHostsList();
        } catch (Exception ignored) {}

        return hosts;
    }
}
