package datastore;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datautil.DataSearchUtility;
import datautil.PrintUtil;
import datautil.ResourceLoader;

/**
 * This abstract class is used to hold all the records from the file.
 * The records are stored using the "_id" so that if searched by "_id", search will be faster.
 */
public abstract class DataStore {

    private final Set<String> fields;
    private final Map<String, Data> idToDataMap;

    /**
     * Construct data from json file
     * @param fileName Name of a file
     */
    protected DataStore(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileUrl = classLoader.getResource(fileName);
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(fileUrl);
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
        return Collections.unmodifiableSet(fields);
    }

    public Map<String, Data> getDataMap() {
        return Collections.unmodifiableMap(idToDataMap);
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
        PrintUtil.printData("\nSearching " + getDataName() + " for " + searchField + " with a value of " + searchValue);
        return DataSearchUtility.searchDatastore(getDataMap(), searchField, searchValue);
    }

    /**
     * Gives the current datastore name, used to print.
     * @return String datastore name
     */
    public abstract String getDataName();

    /**
     * Search the current entity data for matching data.
     * @return List<Data> Matched data
     */
    public abstract List<Data> visit(Data result);

    /**
     * The datastore search method by Id.
     * @param searchValue Searching value
     * @return matched data otherwise null.
     */
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

}
