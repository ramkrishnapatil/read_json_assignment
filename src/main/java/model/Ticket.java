package model;

public class Ticket extends Data {

    private static final String ENTITY_NAME = "Tickets";

    public Ticket(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }

}