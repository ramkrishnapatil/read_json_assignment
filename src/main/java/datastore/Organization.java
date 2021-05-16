package datastore;

import java.util.ArrayList;
import java.util.List;

import datautil.PrintUtil;

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

    @Override
    public List<Data> visit(Data result) {
        List<Data> relatedResults = new ArrayList<>();
        //From user/tickets data, get the organization_id and match it to id in the Organization
        if (result.getFields().containsKey(PrintUtil.ORGANIZATION_ID)) {
            String orgId = result.getFieldValue(PrintUtil.ORGANIZATION_ID).toString();
            relatedResults.add(searchDataStoreById(orgId));
        }
        return relatedResults;
    }

}
