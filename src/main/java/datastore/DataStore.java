package datastore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datautil.DataSearchUtility;
import datautil.PrintUtil;
import datautil.ResourceLoader;

public abstract class DataStore {

    private static final String FIELD_INDENT = " ";
    private final Set<String> fields;
    private final Map<String, Data> idToDataMap;

    /**
     * Construct data from json file
     * @param fileName Name of a file
     */
    protected DataStore(String fileName) {
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(fileName);
        this.fields = DataSearchUtility.searchableFields(dataList);
            idToDataMap = new HashMap<>();
            dataList.forEach(dataRecord -> {
                // Assume that id will always exist
                //store the data in datastore by id
                try {
                    String id = dataRecord.get(PrintUtil.ID_KEY).toString();
                    Data data = new Data(dataRecord);
                    idToDataMap.put(id, data);
                } catch (Exception exception) {
                    PrintUtil.printData(" Error while storing data by id : " + exception.getMessage());
                }
            });
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
        PrintUtil.printData("Searching " + getDataName() + " for " + searchField + " with a value of " + searchValue);
        return DataSearchUtility.searchDatastore(idToDataMap, searchField, searchValue);
    }

    public abstract String getDataName();

    public void printSearchableFields() {
        PrintUtil.printData("---------------------------------------------------------------------------------------------------------");
        PrintUtil.printData("Search " + getDataName() + " with");
        for (String field : getFields()) {
            PrintUtil.print(" |" + FIELD_INDENT + field);
        }
        PrintUtil.print(PrintUtil.NEW_LINE);
    }

    public Data searchDataStoreById(String searchValue) {
        if (isSearchableField(PrintUtil.ID_KEY)) {
            Data dataRecord = idToDataMap.get(searchValue);
            if (dataRecord != null) {
                PrintUtil.printData("\nPrinting " + getDataName() + " for id : " + searchValue);
                return dataRecord;
            }
        }
        return null;
    }

    public List<Data> searchDataStoreByField(String searchField, String searchValue) {
        List<Data> results = new ArrayList<>();

        // searching by id
        if (PrintUtil.ID_KEY.equals(searchField)) {
            results.add(searchDataStoreById(searchValue));
        }
        else {
            idToDataMap.forEach((s, data) -> {
                if (data.getFields().containsKey(searchField) && searchValue.contains(data.getFieldValue(searchField).toString())) {
                    results.add(data);
                }
            });
        }
        return results;
    }

}
