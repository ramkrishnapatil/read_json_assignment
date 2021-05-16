package datastore;

import java.util.List;
import java.util.Set;

import datautil.ConfigFieldsUtil;
import datautil.PrintUtil;

/** Contains the map of Datastore's.
 * Search the Datastore and its related datastore
 */
public class DatastoreManager {

    private final DataStore user;
    private final DataStore ticket;
    private final DataStore organization;
    private final ConfigFieldsUtil configFieldsUtil;

    public DatastoreManager() {
        user = new User("users.json");
        ticket = new Ticket("tickets.json");
        organization = new Organization("organizations.json");
        configFieldsUtil = new ConfigFieldsUtil("configfields.json");
    }

    /**
     * Returns the user datastore
     * @return User
     */
    public DataStore getUser() {
        return user;
    }

    /**
     * Returns the ticket datastore
     * @return Ticket
     */
    public DataStore getTicket() {
        return ticket;
    }

    /**
     * Returns the Organization datastore
     * @return Organization
     */
    public DataStore getOrganization() {
        return organization;
    }

    /**
     * Returns the datastore from name
     * @param dataName Datastore name
     * @return Datastore Matched datastore
     */
    private DataStore getDataStore(String dataName) {
        if (dataName.equals(user.getDataName())) {
            return user;
        }
        else if (dataName.equals(ticket.getDataName())) {
            return ticket;
        }
        else {
            return organization;
        }
    }

    /**
     * Search the Datastore for given field and value.
     * Search the related datastore for found results.
     * @param dataName Datastore name
     * @param searchField Search field
     * @param searchValue Search value
     */
    public void searchAndPrintData(String dataName, String searchField, String searchValue) {
        DataStore datastore = getDataStore(dataName);
        List<Data> results = datastore.search(searchField, searchValue);

        //From Organization data, get the id and match to organization_id in users and tickets
        if (getOrganization().getDataName().equals(dataName)) {
            Set<String> userConfigFields = configFieldsUtil.getConfigFields(getUser().getDataName());
            Set<String> ticketConfigFields = configFieldsUtil.getConfigFields(getTicket().getDataName());
            results.forEach(result -> {
                PrintUtil.printResult(result);
                List<Data> users = getUser().visit(result);
                PrintUtil.printResults(users, userConfigFields);
                List<Data> tickets = getTicket().visit(result);
                PrintUtil.printResults(tickets, ticketConfigFields);
            });
        }
        else {
            //From Users/Tickets find the organization_id and match it to Organization id
            Set<String> orgConfigFields = configFieldsUtil.getConfigFields(getOrganization().getDataName());
            results.forEach(result -> {
                PrintUtil.printResult(result);
                List<Data> organizations = getOrganization().visit(result);
                PrintUtil.printResults(organizations, orgConfigFields);
            });
        }
    }
}
