package datastore;

public class User extends DataStore {

    private static final String ENTITY_NAME = "Users";

    /**
     * Construct data from json file
     *
     * @param fileName Name of a file in the resources directory
     */
    public User(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }
}
