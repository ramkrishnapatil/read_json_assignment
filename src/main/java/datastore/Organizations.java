package datastore;

public class Organizations extends DataStore {

    private static final String ENTITY_NAME = "Organization";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public Organizations(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
