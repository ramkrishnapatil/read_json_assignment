package datastore;

public class Ticket extends DataStore {

    private static final String ENTITY_NAME = "Tickets";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public Ticket(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
