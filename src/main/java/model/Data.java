package model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashBiMap;
import util.DataSearchUtility;
import util.PrintDataUtil;
import util.ResourceLoader;

public abstract class Data {

    private static final String FIELD_INDENT = " ";
    private final Set<String> fields;
    protected static final String ID_KEY = "_id";
    private List<Map<String, Object>> jsonData;
    private Map<String, Integer> idToRecordNb;

    /**
     * Construct data from json file
     * @param fileName Name of a file in the resources directory
     */
    protected Data(String fileName) {
        this.jsonData = ResourceLoader.loadFromJsonResource(fileName);
        this.fields = DataSearchUtility.searchableFields(jsonData);
        putRecordNbToId();
    }

    private void putRecordNbToId() {
        idToRecordNb = HashBiMap.create();
        if (hasData()) {
            try {
                for (int recordNb = 0; recordNb < jsonData.size(); recordNb++) {
                    String id = jsonData.get(recordNb).get(ID_KEY).toString();
                    idToRecordNb.put(id, recordNb);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
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
        return !jsonData.isEmpty() && !fields.isEmpty();
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
     *
     * @see DataSearchUtility#search(List, String, String)
     *
     * @param searchField Field/key within the maps to match upon
     * @param searchValue Value to look for in the search field
     * @return List of data items that contain a field with a name of the
     *         search field and have a value that matches the search value
     */
    public List<Map<String, Object>> search(String searchField, String searchValue) {
        return DataSearchUtility.search(jsonData, searchField, searchValue);
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

    public void searchIdAndPrintRecord(Set<String> searchValues) {
        if (isSearchableField(ID_KEY)) {
            searchValues.forEach(searchValue -> {
                Map<String, Object> results = jsonData.get(idToRecordNb.get(searchValue));
                System.out.print("\nPrinting " + getDataName() + " for id : " + searchValue);
                PrintDataUtil.printResults(results);
                results.clear();
            });
        }
    }
}
