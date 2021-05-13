package datautil;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class provides the configuration of fields to be included from the Datastore.
 * e.g. User searches Organizations for _id = 1. Tickets/Users have entries for organization_id = 1
 * Search matches the record from Tickets/Users.
 * Using this file user can decide which fields to be displayed from Tickets/Users.
 * e.g. User can configure Tickets for name field
 * If configuration not defined then all the fields will be displayed
 */
public class ConfigFieldsUtil {

    private final Map<String, Set<String>> configFields;

    /**
     * Construct data from json file
     * @param fileName Name of a file
     */
    public ConfigFieldsUtil(String fileName) {
        configFields = new HashMap<>();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            List<Map<String, Object>> configData = ResourceLoader.loadFromJsonResource(file.toString());

            configData.forEach(config -> {
                config.entrySet().forEach(stringObjectEntry -> {
                    Set<String> dataFields = new HashSet<>(((List<String>)stringObjectEntry.getValue()));
                    configFields.put(stringObjectEntry.getKey(), dataFields);
                });
            });
        } catch (NullPointerException npe) {
            PrintUtil.printData("Unable to open jsonFile: " + fileName + " : " + npe.getMessage());
        } catch (Exception exc) {
            PrintUtil.printData("Exception in loading jsonFile: " + fileName + " : "  + exc.getMessage());
        }
    }

    public Set<String> getConfigFields(String dataName) {
        if (configFields.get(dataName) != null) {
            return Collections.unmodifiableSet(configFields.get(dataName));
        }
        else {
            return Collections.unmodifiableSet(new HashSet<>());
        }
    }

}
