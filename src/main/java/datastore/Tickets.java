package datastore;

public class Tickets extends DataStore {

    private static final String ENTITY_NAME = "Tickets";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public Tickets(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
