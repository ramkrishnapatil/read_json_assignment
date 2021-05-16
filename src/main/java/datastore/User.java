package datastore;

import java.util.ArrayList;
import java.util.List;

import datautil.PrintUtil;

/**
 * This will contain all the User records.
 */
public class User extends DataStore {

    private static final String ENTITY_NAME = "Users";

    /**
     * Construct data from json file
     * @param fileName Name of a file in the resources directory
     */
    public User(String fileName) {
        super(fileName);
    }

    @Override
    public String getDataName() {
        return ENTITY_NAME;
    }

    @Override
    public List<Data> visit(Data result) {
        List<Data> relatedResults = new ArrayList<>();

        // Search tickets for org_id
        if (result.getId() != null && !result.getId().isEmpty()) {
            relatedResults.addAll(search(PrintUtil.ORGANIZATION_ID, result.getId()));
        }

        return relatedResults;
    }

}
