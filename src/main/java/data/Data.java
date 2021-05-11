package data;

import java.util.HashMap;
import java.util.Map;

//public abstract class Data {
public class Data {

    private Map<String, Object> fields;
    private String id;

    public Data(String id, Map<String, Object> fieldsMap) {
        fields = new HashMap<>();
        this.id = id;
        this.fields.putAll(fieldsMap);
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public Object getFieldValue(String fieldName) {
        return fields.get(fieldName);
    }

}
