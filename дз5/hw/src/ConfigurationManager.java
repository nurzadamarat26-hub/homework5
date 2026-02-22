import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {


    private static volatile ConfigurationManager instance;

    private Map<String, String> settings;


    private ConfigurationManager() {
        settings = new HashMap<>();
    }


    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }


    public void setSetting(String key, String value) {
        settings.put(key, value);
    }


    public String getSetting(String key) throws Exception {
        if (!settings.containsKey(key)) {
            throw new Exception("Настройка с ключом '" + key + "' не найдена.");
        }
        return settings.get(key);
    }


    public void loadFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                settings.put(parts[0].trim(), parts[1].trim());
            }
        }
        reader.close();
    }


    public void saveToFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        for (Map.Entry<String, String> entry : settings.entrySet()) {
            writer.write(entry.getKey() + "=" + entry.getValue());
            writer.newLine();
        }

        writer.close();
    }


    public void loadFromDatabase() {

        settings.put("db.url", "localhost:5432");
        settings.put("db.user", "admin");
        settings.put("db.password", "1234");
    }
}