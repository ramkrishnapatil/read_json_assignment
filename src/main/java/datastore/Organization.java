package datastore;

/**
 * This will contain all the Organization records.
 */
public class Organization extends DataStore {

    private static final String ENTITY_NAME = "Organizations";

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
