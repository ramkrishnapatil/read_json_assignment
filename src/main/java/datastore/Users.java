package datastore;

public class Users extends DataStore {

    private static final String ENTITY_NAME = "Users";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public Users(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
