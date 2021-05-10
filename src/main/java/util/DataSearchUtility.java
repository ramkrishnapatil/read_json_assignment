package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Search Utility functions.
 */
public final class DataSearchUtility {

    /**
     * Search the given list of data for items that have the given field and
     * it's value matches (or contains in the case of lists) the given search value.
     * @param data List of data to search, the keys correspond to the search field
     * @param searchField Field/key within the maps to match upon
     * @param searchValue Value to look for in the search field
     * @return List of data items that contain a field with a name of the
     *         search field and have a value that matches the search value
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> search(List<Map<String, Object>> data, String searchField, String searchValue) {
        List<Map<String, Object>> results = new ArrayList<>();

        if (null == data) {
            return results;
        }

        for (Map<String, Object> listItem : data) {
            Object fieldValue = listItem.get(searchField);

            // Consider a null value as empty
            if (null == fieldValue) {
                fieldValue = "";
            }

            if (fieldValue instanceof Double) {
                try {
                    if (fieldValue.equals(Double.valueOf(searchValue))) {
                        results.add(listItem);
                    }
                } catch (NumberFormatException nfe) {
                    // Continue
                }
            }
            else if (fieldValue instanceof List) {
                // List/Arrays are assumed to only contain strings
                if (((List<String>)fieldValue).contains(searchValue)) {
                    results.add(listItem);
                }
            }
            else {
                if (fieldValue.toString().equals(searchValue)) {
                    results.add(listItem);
                }
            }
        }

        return results;
    }

    /**
     * Get a set of all the fields from the given data.
     * @param dataModel List of data items that have searchable fields/keys
     * @return a set of all the possible searchable fields in the data
     */
    public static Set<String> searchableFields(List<Map<String, Object>> dataModel) {
        Set<String> searchableFields = new TreeSet<>();

        if (null == dataModel) {
            return searchableFields;
        }

        for (Map<String, Object> data : dataModel) {
            searchableFields.addAll(data.keySet());
        }
        return searchableFields;
    }
}