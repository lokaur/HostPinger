package pinger.model;

import org.json.simple.JSONObject;

import java.util.Vector;

public class Host {
    public static final String URI_KEY = "uri";

    public static final String NAME_KEY = "name";

    public static final String AVAILABLE_KEY = "available";

    private String uri;

    private String name;

    private boolean isAvailable;

    public Host(String name, String uri) {
        this.uri = uri;
        this.name = name;
    }

    public Host(String name, String uri, boolean isAvailable) {
        this.uri = uri;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public String getUri() {
        return uri;
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

        return host.uri.equals(this.uri);
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME_KEY, name);
        jsonObject.put(URI_KEY, uri);
        jsonObject.put(AVAILABLE_KEY, isAvailable);
        return jsonObject;
    }

    public Object[] toRowData() {
        return new Object[] {this};
    }

    public static Host getHostFromJson(JSONObject jsonObject) {
        String name = jsonObject.get(NAME_KEY).toString();
        String uri = jsonObject.get(URI_KEY).toString();
        boolean available = Boolean.valueOf(jsonObject.get(AVAILABLE_KEY).toString());

        return new Host(name, uri, available);
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

    public void setName(String name) {
        this.name = name;
    }
}
