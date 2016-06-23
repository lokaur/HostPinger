package pinger.model;

import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import pinger.view.MainView;

import java.util.ArrayList;

public class Pinger {
    public static void checkHosts(MainView view, ArrayList<Host> hosts) throws Exception {
        for (Host host : hosts) {
            try {
                final IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
                request.setHost(host.getUri());
                final IcmpPingResponse response = IcmpPingUtil.executePingRequest (request);
                boolean isAvailable = response.getSuccessFlag();

                if (host.isAvailable() != isAvailable) {
                    host.setAvailable(isAvailable);
                    JsonWriter.updateHost(host);
                    view.reload();
                }

            } catch (final Throwable t) {
                if (host.isAvailable()) {
                    host.setAvailable(false);
                    JsonWriter.updateHost(host);
                    view.reload();
                }
            }
        }
    }
}
