package datautil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Load the json resource into data.
 */
public final class ResourceLoader {

    private static final String RESOURCE_PATH = "src/main/resources/";
    private static final String ERROR_REASON = "reason";

    private ResourceLoader() {
        // instantiation not required
    }

    /**
     * Deserialize the JSON from the given jsonFile to a list of maps.
     * Map keys are the field names and the values are appropriate objects.
     * @param jsonFile Resource JSON file to deserialize
     * @return A list of maps read from the JSON file
     */
    public static List<Map<String, Object>> loadFromJsonResource(String jsonFile) {
        List<Map<String, Object>> data = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            File usersFile = Paths.get(RESOURCE_PATH + jsonFile).toFile();
            data = mapper.readValue(usersFile, ArrayList.class);
        } catch (NullPointerException npe) {
            System.err.println("Unable to open jsonFile: " + jsonFile + " : " + npe.getMessage());
        } catch (JsonParseException jse) {
            System.err.println("Invalid data in jsonFile: " + jsonFile + " : "  + jse.getMessage());
        } catch (JsonMappingException jme) {
            System.err.println("Error while loading jsonFile: " + jsonFile + " : "  + jme.getMessage());
        } catch (IOException ioe) {
            System.err.println("Error while loading jsonFile: " + jsonFile + " : "  + ioe.getMessage());
        }

        return data;
    }
}
