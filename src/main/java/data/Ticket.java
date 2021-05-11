package data;

import java.util.Map;

public class Ticket extends Data {

    private static final String ENTITY_NAME = "Tickets";

    public Ticket(String id, Map<String, Object> fieldsMap) {
        super(id, fieldsMap);
    }

}