package datastore;

public class Organization extends DataStore {

    private static final String ENTITY_NAME = "Organization";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public Organization(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
