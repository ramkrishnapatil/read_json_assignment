package datautil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigFieldsUtil {

    private final Map<String, Set<String>> configFields;

    /**
     * Construct data from json file
     * @param fileName Name of a file
     */
    public ConfigFieldsUtil(String fileName) {
        List<Map<String, Object>> configData = ResourceLoader.loadFromJsonResource(fileName);
        configFields = new HashMap<>();

        configData.forEach(config -> {
            config.entrySet().forEach(stringObjectEntry -> {
                Set<String> dataFields = new HashSet<String>(((List<String>)stringObjectEntry.getValue()));
                configFields.put(stringObjectEntry.getKey(), dataFields);
            });
        });
    }

    public Set<String> getConfigFields(String dataName) {
        return configFields.get(dataName);
    }

}
