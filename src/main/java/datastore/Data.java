package datastore;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import datautil.PrintUtil;

/**
 * The class represents the User/Organization/Ticket entity.
 */
public class Data {

    private final Map<String, Object> fields;

    public Data(Map<String, Object> fieldsMap) {
        fields = new LinkedHashMap<>();
        this.fields.putAll(fieldsMap);
    }

    public String getId() {
        return fields.get(PrintUtil.ID_KEY).toString();
    }

    public Map<String, Object> getFields() {
        return Collections.unmodifiableMap(fields);
    }

    public Object getFieldValue(String fieldName) {
        return fields.get(fieldName);
    }

}
