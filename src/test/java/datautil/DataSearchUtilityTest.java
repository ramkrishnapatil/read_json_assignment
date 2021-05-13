package datautil;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import datastore.Data;
import datastore.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataSearchUtilityTest {

    private User dataStore;
    private Map<String, Data> dataMap;
    private static final String USERS_FILE_NAME = "users.json";

    @Before
    public void setUp() {
        dataStore = new User(USERS_FILE_NAME);
        dataMap = dataStore.getDataMap();
    }

    @After
    public void tearDown() {
        dataStore = null;
    }

    @Test
    public void searchDatastoreForNotMatchingValue() {
        String searchField = "name";
        String searchValue = "value";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertTrue(matchedData.isEmpty());
    }

    @Test
    public void searchDatastoreForNotMatchingKey() {
        String searchField = "name1";
        String searchValue = "Francisca Rasmussen";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertTrue(matchedData.isEmpty());
    }

    @Test
    public void searchDatastoreForMatchingKeyAndValue() {
        String searchField = "name";
        String searchValue = "Francisca Rasmussen";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertFalse(matchedData.isEmpty());
    }

    @Test
    public void searchDatastoreForMultipleMatchingKeyAndValue() {
        String searchField = "alias";
        String searchValue = "Miss Coffey";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(3, matchedData.size());
    }

    @Test
    public void searchDatastoreWithNonexistId() {
        String searchField = "_id";
        String searchValue = "10";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertTrue(matchedData.isEmpty());
    }

    @Test
    public void searchDatastoreWithExistingId() {
        String searchField = "_id";
        String searchValue = "1";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(1, matchedData.size());
    }

    @Test
    public void datastoreRecordWithNoId() {
        String searchField = "organization_id";
        String searchValue = "105";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(0, matchedData.size());
    }

    @Test
    public void searchDatastoreWithNonExistingDouble() {
        String searchField = "double";
        String searchValue = "37.999";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(0, matchedData.size());

        searchValue = "37.999999999";
        matchedData.clear();
        matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(0, matchedData.size());
    }

    @Test
    public void searchDatastoreDoubleWithNonDouble() {
        String searchField = "double";
        String searchValue = "abcd";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(0, matchedData.size());
    }

    @Test
    public void searchDatastoreWithExistingDouble() {
        String searchField = "double";
        String searchValue = "37.99";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(1, matchedData.size());

        searchValue = "37.9900000";
        matchedData.clear();
        matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(1, matchedData.size());
        assertEquals("1", matchedData.get(0).getId());
    }

    @Test
    public void searchDatastoreForList() {
        String searchField = "tags";
        String searchValue = "Springville";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(1, matchedData.size());

        searchValue = "Australia";
        matchedData.clear();
        matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals(0, matchedData.size());
    }

    @Test
    public void searchableFieldExist() {
        ClassLoader classLoader = getClass().getClassLoader();
        File fileName = new File(classLoader.getResource(USERS_FILE_NAME).getFile());
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(fileName.toString());
        String searchFieldExist = "_id";
        assertTrue(DataSearchUtility.searchableFields(dataList).contains(searchFieldExist));
        String searchFieldNotExist = "id";
        assertFalse(DataSearchUtility.searchableFields(dataList).contains(searchFieldNotExist));
    }

}