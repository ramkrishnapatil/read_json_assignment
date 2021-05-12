package data;

import java.util.HashMap;
import java.util.Map;

import datautil.PrintUtil;

public class Data {

    private Map<String, Object> fields;

    public Data(Map<String, Object> fieldsMap) {
        fields = new HashMap<>();
        this.fields.putAll(fieldsMap);
    }

    public String getId() {
        return fields.get(PrintUtil.ID_KEY).toString();
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public Object getFieldValue(String fieldName) {
        return fields.get(fieldName);
    }

}
