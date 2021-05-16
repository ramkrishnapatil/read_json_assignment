package datastore;

import java.util.ArrayList;
import java.util.List;

import datautil.PrintUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatastoreManagerTest {

    DatastoreManager datastoreManager;

    @Before
    public void setUp() {
        datastoreManager = new DatastoreManager();
    }

    @After
    public void tearDown() {
        datastoreManager = null;
    }

    @Test
    public void searchOrganizationAndRelatedUsersDatastore() {
        String organizationId = "101";
        List<Data> organizations = datastoreManager.getOrganization().search (PrintUtil.ID_KEY, organizationId);
        Assert.assertEquals("Total results : ",  1, organizations.size());
        List<Data> users = new ArrayList<>();
        List<Data> tickets = new ArrayList<>();
        organizations.forEach(org -> {
            users.addAll(datastoreManager.getUser().visit(org));
            tickets.addAll(datastoreManager.getTicket().visit(org));
        });
        Assert.assertEquals(1, users.size());
        Assert.assertEquals("Matching org_id result in users : ", "2", users.get(0).getId());
        Assert.assertEquals(1, tickets.size());
        Assert.assertEquals("Matching org_id result in tickets : ", "436bf9b0-1147-4c0a-8439-6f79833bff5b", tickets.get(0).getId());
    }

    @Test
    public void searchUsersDatastore() {
        String userId = "2";
        int orgId = 101;
        List<Data> dataList = datastoreManager.getUser().search(PrintUtil.ID_KEY, userId);
        Assert.assertEquals("Total results : ",  1, dataList.size());
        Assert.assertEquals("Matching org_id result in user : ", orgId, dataList.get(0).getFieldValue(PrintUtil.ORGANIZATION_ID));
        List<Data> org = new ArrayList<>();
        dataList.forEach(user -> {
            org.addAll(datastoreManager.getOrganization().visit(user));
        });
        Assert.assertEquals("Matching org_id result in organization : ", Integer.toString(orgId), org.get(0).getId());
    }

    @Test
    public void searchTicketsDatastore() {
        String tagsValue = "Ohio";
        int orgId = 101;
        List<Data> dataList = datastoreManager.getTicket().search("tags", tagsValue);
        Assert.assertEquals("Total results : ",  1, dataList.size());
        Assert.assertEquals("Matching org_id result in user : ", orgId, dataList.get(0).getFieldValue(PrintUtil.ORGANIZATION_ID));
        List<Data> org = new ArrayList<>();
        dataList.forEach(user -> {
            org.addAll(datastoreManager.getOrganization().visit(user));
        });
        Assert.assertEquals("Matching org_id result in organization : ", Integer.toString(orgId), org.get(0).getId());
    }
}
