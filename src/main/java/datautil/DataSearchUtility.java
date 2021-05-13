package datautil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import datastore.Data;

/**
 * The Search Utility class provides the search .
 */
public final class DataSearchUtility {

    private DataSearchUtility() {
        //Prevent
    }

    /**
     * Search the given list of data for items that have the given field and
     * it's value matches (or contains in the case of lists) the given search value.
     * @param data List of data to search, the keys correspond to the search field
     * @param searchField Field/key within the maps to match upon
     * @param searchValue Value to look for in the search field
     * @return List of matched data items
     */
    @SuppressWarnings("unchecked")
    public static List<Data> searchDatastore(Map<String, Data> data, String searchField, String searchValue) {
        List<Data> results = new ArrayList<>();

        if (null == data) {
            return results;
        }

        // If searching by key and matches value
        if (searchField.equals(PrintUtil.ID_KEY)) {
            Data resultData = data.get(searchValue);
            if (resultData != null) {
                results.add(data.get(searchValue));
            }
            return results;
        }

        for (Map.Entry<String, Data> records : data.entrySet()) {
            Data dataItem = records.getValue();
            if (matchData(dataItem, searchField, searchValue) != null) {
                results.add(dataItem);
            }
        }

        return results;
    }

    /**
     * Search the given data for items that have the given field and it's value matches
     * (or contains in the case of lists) the given search value.
     * @param dataItem Data to search
     * @param searchField Field/key within the maps to match upon
     * @param searchValue Value to look for in the search field
     * @return Data matched data item
     */
    public static Data matchData(Data dataItem, String searchField, String searchValue) {
        Object fieldValue = dataItem.getFieldValue(searchField);

        // Consider a null value as empty
        if (null == fieldValue) {
            fieldValue = "";
        }

        if (fieldValue instanceof Double) {
            try {
                double val = Double.parseDouble(searchValue);
                if (((Double)fieldValue).compareTo(val) == 0) {
                    return dataItem;
                }
            }
            catch (NumberFormatException nfe) {
                PrintUtil.printData("For field " + searchField + " with value " + fieldValue + ", datatype double expected. Provided : " + searchValue);
                // Continue
            }
        }
        else if (fieldValue instanceof List) {
            // List/Arrays are assumed to only contain strings
            if (((List<String>)fieldValue).contains(searchValue)) {
                return dataItem;
            }
        }
        else {
            if (fieldValue.toString().equals(searchValue)) {
                return dataItem;
            }
        }

        return null;
    }

    /**
     * Get a set of all the fields from the given data.
     * @param datastore List of data items that have searchable fields/keys
     * @return a set of all the possible searchable fields in the data
     */
    public static Set<String> searchableFields(List<Map<String, Object>> datastore) {
        Set<String> searchableFields = new TreeSet<>();

        if (null == datastore) {
            return searchableFields;
        }

        for (Map<String, Object> data : datastore) {
            searchableFields.addAll(data.keySet());
        }
        return searchableFields;
    }
}