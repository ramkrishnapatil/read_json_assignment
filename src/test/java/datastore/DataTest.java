package datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import datautil.DataSearchUtility;
import datautil.PrintUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataTest {

    private User users;
    Map<String, Data> dataMap;

    @Before
    public void setUp() {
        users = new User("users.json");
        dataMap = users.getDataMap();
    }

    @After
    public void tearDown() {
        users = null;
    }

    @Test
    public void searchUsersData() {
        // 3 valid records with ids 1,2,3
        assertEquals(4, dataMap.size());
        assertNotNull(dataMap.get("1"));
        assertNull(dataMap.get("5"));

        String searchField = PrintUtil.ID_KEY;
        String searchValue = "name";
        List<Data> matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertTrue(matchedData.isEmpty());

        searchValue = "1";
        matchedData = DataSearchUtility.searchDatastore(dataMap, searchField, searchValue);
        assertEquals("1" , matchedData.get(0).getId());
    }

    @Test
    public void getFields() {
        assertFalse(users.getDataMap().get("1").getFields().isEmpty());
        assertTrue(users.getDataMap().get("1").getFields().containsKey("_id"));
        assertFalse(users.getDataMap().get("1").getFields().containsKey("id"));
    }
}
