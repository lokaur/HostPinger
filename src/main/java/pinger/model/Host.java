package pinger.model;

import org.json.simple.JSONObject;
import sun.security.x509.AVA;

import java.util.ArrayList;
import java.util.Vector;

public class Host {
    public static final String URI_KEY = "uri";

    public static final String PORT_KEY = "port";

    public static final String NAME_KEY = "name";

    public static final String AVAILABLE_KEY = "available";

    private String uri;

    private int port;

    private String name;

    private boolean isAvailable;

    public Host(String name, String uri, int port) {
        this.uri = uri;
        this.port = port;
        this.name = name;
    }

    public Host(String name, String uri, int port, boolean isAvailable) {
        this.uri = uri;
        this.port = port;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getUri() {
        return uri;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Host))
            return false;
        Host host = (Host) obj;

        return host.port == this.port && host.uri.equals(this.uri);
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME_KEY, name);
        jsonObject.put(URI_KEY, uri);
        jsonObject.put(PORT_KEY, port);
        jsonObject.put(AVAILABLE_KEY, isAvailable);
        return jsonObject;
    }

    public Object[] toRowData() {
        return new Object[]{this};
    }

    public static Host getHostFromJson(JSONObject jsonObject) {
        String name = jsonObject.get(NAME_KEY).toString();
        String uri = jsonObject.get(URI_KEY).toString();
        int port = Integer.valueOf(jsonObject.get(PORT_KEY).toString());
        boolean available = Boolean.valueOf(jsonObject.get(AVAILABLE_KEY).toString());

        return new Host(name, uri, port, available);
    }

    public static Host getHostFromObject(Object obj) {
        return (Host) ((Vector) obj).get(0);
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }
}
