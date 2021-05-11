package datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import data.Data;
import datautil.DataSearchUtility;
import datautil.ResourceLoader;

public abstract class DataStore {

    private static final String FIELD_INDENT = " ";
    private final Set<String> fields;
    protected static final String ID_KEY = "_id";
    private Map<String, Data> idToDataMap;

    /**
     * Construct data from json file
     * @param fileName Name of a file
     */
    public DataStore(String fileName) {
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(fileName);
        this.fields = DataSearchUtility.searchableFields(dataList);
        //store the data in datastore by id
        try {
            idToDataMap = new HashMap<>();
            dataList.forEach(record -> {
                // Assume that id will always exist
                String id = record.get(ID_KEY).toString();
                Data data = new Data(id, record);
                idToDataMap.put(id, data);
            });
        } catch (Exception exception) {
            System.out.println(" Error while storing data : " + exception.getMessage());
        }
    }

    /**
     * Get all of the available fields.
     * @return all the searchable fields
     */
    public Set<String> getFields() {
        return this.fields;
    }

    /**
     * Check if data is available
     * @return true if there is data otherwise false
     */
    public boolean hasData() {
        return !idToDataMap.isEmpty() && !fields.isEmpty();
    }

    /**
     * Determine if the given field is a available.
     * @param field field to search
     * @return true if the field exists in fields
     */
    public boolean isSearchableField(String field) {
        return fields.contains(field);
    }

    /**
     * Search the data for items that have the given search field
     * and whose value matches (or contains in the case of lists) the given
     * search value.
     * @param searchField Field name
     * @param searchValue field value
     * @return List of data items that contain a field with a name of the
     *         search field and matches the search value
     */
    public List<Data> search(String searchField, String searchValue) {
        System.out.println("Searching " + getDataName() + " for " + searchField + " with a value of " + searchValue);
        return DataSearchUtility.search(idToDataMap, searchField, searchValue);
    }

    public abstract String getDataName();

    public void printSearchableFields() {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("Search " + getDataName() + " with");
        for (String field : getFields()) {
            System.out.print(" |" + FIELD_INDENT + field);
        }
        System.out.println();
    }

    public List<Data> searchDataStore(Set<String> searchValues) {
        List<Data> results = new ArrayList<>();
        if (isSearchableField(ID_KEY)) {
            searchValues.forEach(searchValue -> {
                Data record = idToDataMap.get(searchValue);
                System.out.print("\nPrinting " + getDataName() + " for id : " + searchValue);
                results.add(record);
            });
        }
        return results;
    }

}
