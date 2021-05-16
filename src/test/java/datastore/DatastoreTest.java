package datastore;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatastoreTest {

    private Organization organization;
    private User users;
    private Ticket tickets;

    @Before
    public void setUp() {
        organization = new Organization("organizations.json");
        users = new User("users.json");
        tickets = new Ticket("tickets.json");
    }

    @After
    public void tearDown() {
        organization = null;
        users = null;
        tickets = null;
    }

    @Test
    public void datastoreName() {
        Assert.assertEquals("Organizations", organization.getDataName());
        Assert.assertEquals("Users", users.getDataName());
        Assert.assertEquals("Tickets", tickets.getDataName());
    }

    @Test
    public void getFields() {
        Assert.assertFalse(organization.getFields().isEmpty());
    }

    @Test
    public void getDataMap() {
        Assert.assertFalse(organization.getDataMap().isEmpty());
    }

    @Test
    public void hasData() {
        Assert.assertTrue(organization.hasData());
    }

    @Test
    public void isSearchableField() {
        Assert.assertTrue(organization.isSearchableField("tags"));
        Assert.assertFalse(organization.isSearchableField("tags1"));
    }

    @Test
    public void searchDatastoreById() {
        String organizationIDValue = "101";
        Data dataFound = organization.searchDataStoreById(organizationIDValue);
        Assert.assertNotNull(dataFound);

        organizationIDValue = "201";
        Data dataNotFound = organization.searchDataStoreById(organizationIDValue);
        Assert.assertNull(dataNotFound);
    }

    @Test
    public void searchRelatedDatastore() {
        String organizationIDValue = "101";
        List<Data> dataList = organization.search("_id", organizationIDValue);
        Assert.assertFalse(dataList.isEmpty());

        organizationIDValue = "201";
        Data dataNotFound = organization.searchDataStoreById(organizationIDValue);
        Assert.assertNull(dataNotFound);
    }

    @Test
    public void searchDatastoreByFieldName() {
        String searchName = "tags";
        String searchValue = "Cherry";
        List<Data> dataList = organization.search(searchName, searchValue);
        Assert.assertEquals(2, dataList.size());

        //Value does not exist
        searchValue = "201";
        dataList.clear();
        dataList = organization.search(searchName, searchValue);
        Assert.assertEquals(0, dataList.size());
    }

    @Test
    public void searchDatastoreForNullValue() {
        String searchName = "locale";
        String searchValue = "";
        List<Data> dataList = users.search(searchName, searchValue);
        Assert.assertEquals(1, dataList.size());
    }
}
