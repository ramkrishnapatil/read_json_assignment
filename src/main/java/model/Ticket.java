package model;

public class Ticket extends Data {

    private static final String entityName = "Tickets";

    public Ticket(String resource) {
        super(resource);
    }

    @Override
    public String getDataName() {
        return entityName;
    }

}