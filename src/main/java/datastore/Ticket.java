package datastore;

import java.util.ArrayList;
import java.util.List;

import datautil.PrintUtil;

/**
 * This will contain all the Ticket records.
 */
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
