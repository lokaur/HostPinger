package pinger.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pinger.exceptions.HostExistsException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class JsonWriter {
    private static final String HOSTS = "resources//hosts.json";

    private static JSONObject GetJsonObject() throws ParseException, IOException {
        return (JSONObject) new JSONParser().parse(new FileReader(HOSTS));
    }

    public static void addHost(Host host) throws HostExistsException, IOException, ParseException {
        JSONObject jsonObject = GetJsonObject();

        if (IsExists(host))
            throw new HostExistsException();

        jsonObject.put(jsonObject.size(), host.toJson());

        saveJsonToFile(jsonObject);
    }

    private static void saveJsonToFile(JSONObject jsonObject) throws IOException {
        FileWriter fw = new FileWriter(HOSTS);
        fw.write(jsonObject.toJSONString());
        fw.flush();
        fw.close();
    }

    public static void updateHost(Host oldHost) throws Exception {
        List<Host> hosts = loadHostsList();

        hosts.stream().filter(host -> host.equals(oldHost)).forEach(host -> {
            host.setAvailable(oldHost.isAvailable());
            host.setName(oldHost.getName());
            host.setPort(oldHost.getPort());
            host.setUri(oldHost.getUri());
        });

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < hosts.size(); i++) {
            Host host = hosts.get(i);
            jsonObject.put(i, host.toJson());
        }

        saveJsonToFile(jsonObject);
    }

    private static boolean IsExists(Host newHost) throws ParseException, IOException {
        List<Host> hosts = loadHostsList();

        for (Host host : hosts)
            if (host.equals(newHost))
                return true;

        return false;
    }

    public static ArrayList<Host> loadHostsList() throws IOException, ParseException {
        JSONObject jsonHosts = GetJsonObject();
        ArrayList hosts = new ArrayList<>();

        for (int i = 0; i < jsonHosts.size(); i++)
            hosts.add(Host.getHostFromJson((JSONObject) jsonHosts.get(String.valueOf(i))));

        return hosts;
    }

    public static void saveVectorToJson(Vector vector) {
        ArrayList<Host> hosts = new ArrayList<>();
        for (Object obj : vector) {
            hosts.add(Host.getHostFromObject(obj));
        }

        try {
            saveHosts(hosts);
        } catch (IOException ignored) {}
    }

    private static void saveHosts(ArrayList<Host> hosts) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for (Host host : hosts) {
            jsonObject.put(jsonObject.size(), host.toJson());
        }

        saveJsonToFile(jsonObject);
    }
}
