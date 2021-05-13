package search;

import static datautil.PrintUtil.HYPHENS_LINE;
import static datautil.PrintUtil.INPUT_PROMPT;
import static datautil.PrintUtil.QUIT_STRING;
import static datautil.PrintUtil.SEARCH_FIELD_NAME;
import static datautil.PrintUtil.SEARCH_FIELD_VALUE;

import java.util.List;
import java.util.Scanner;

import datastore.Data;
import datastore.DataStore;
import datastore.Organization;
import datastore.Ticket;
import datastore.User;
import datautil.ConfigFieldsUtil;
import datautil.PrintUtil;

/**
 * This class creates Users/Tickets/Organizations from the  json files.
 * This classes are used to search/display data.
 */
public class ZendeskSearchApp {
    private final User user;
    private final Ticket ticket;
    private final Organization organization;
    private final ConfigFieldsUtil configFieldsUtil;

    /**
     * Default constructor. This will load the data into java objects.
     */
    public ZendeskSearchApp() {
        user = new User("users.json");
        ticket = new Ticket("tickets.json");
        organization = new Organization("organizations.json");
        configFieldsUtil = new ConfigFieldsUtil("configfields.json");
    }

    /**
     * Search the data
     * @param menuOption user menu option
     * @param scanner Scanner for user input
     */
    public void searchData(String menuOption, Scanner scanner) {
        DataStore data;
        switch (menuOption) {
        case "1":
            data = user;
            break;
        case "2":
            data = ticket;
            break;
        case "3":
            data = organization;
            break;
        default:
            PrintUtil.printData(PrintUtil.INVALID_OPTION);
            return;
        }

        if (!data.hasData()) {
            PrintUtil.print(PrintUtil.NEW_LINE);
            PrintUtil.printData("Nothing to search!");
            return;
        }

        String searchField = promptForSearchableField(data, scanner);
        String searchValue = promptForSearchValue(scanner);
        List<Data> results = data.search(searchField, searchValue);

        searchFromRelatedDatastore(menuOption, results);
    }

    /**
     * This function will search the related data from different datastore
     * @param menuOption Selected menu option
     * @param results Results searched
     */
    private void searchFromRelatedDatastore(String menuOption, List<Data> results) {
        if (results.isEmpty()) {
            PrintUtil.printResults(results);
            return;
        }
        //If the organization_id and tickets/users id are same then we will need two sets
        switch (menuOption) {
        case "1":
        case "2":
            // Currently do not know the relation of users and tickets so not implemented
            // Otherwise it case 2 will be separate.
            results.forEach(result -> {
                PrintUtil.printResult(result);
                if (result.getFields().containsKey(PrintUtil.ORGANIZATION_ID)) {
                    String orgId = result.getFieldValue(PrintUtil.ORGANIZATION_ID).toString();
                    PrintUtil.printResult(organization.searchDataStoreById(orgId), configFieldsUtil.getConfigFields(organization.getDataName()));
                }
            });
            break;
        case "3":
            results.forEach(result -> {
                PrintUtil.printResult(result);
                if (result.getId() != null && !result.getId().isEmpty()) {
                    PrintUtil.printResults(user.search(PrintUtil.ORGANIZATION_ID, result.getId()), configFieldsUtil.getConfigFields(user.getDataName()));
                    PrintUtil.printResults(ticket.search(PrintUtil.ORGANIZATION_ID, result.getId()), configFieldsUtil.getConfigFields(ticket.getDataName()));
                }
            });
            break;
        default:
            break;
        }
    }

    /**
     * Get the search field name from user.
     * @param data Datastore to search the field
     * @param scanner System input
     * @return String Valid Search field name
     */
    private String promptForSearchableField(DataStore data, Scanner scanner) {
        String searchableField;

        boolean validField;
        do {
            PrintUtil.printData(SEARCH_FIELD_NAME);
            PrintUtil.print(INPUT_PROMPT);
            searchableField = scanner.nextLine().trim();
            if (QUIT_STRING.equalsIgnoreCase(searchableField)) {
                exitApplication();
            }

            validField = data.isSearchableField(searchableField);
            if (!validField) {
                PrintUtil.printData("Invalid field, try again. Valid fields are : ");
                PrintUtil.printFields(data.getDataName(), data.getFields());
                PrintUtil.printData(HYPHENS_LINE);
            }
        } while (!validField);

        return searchableField;
    }

    /**
     * Prompt for search value
     * @param scanner
     * @return search field value
     */
    private String promptForSearchValue(Scanner scanner) {
        PrintUtil.printData(SEARCH_FIELD_VALUE);
        PrintUtil.print(INPUT_PROMPT);
        String searchValue = scanner.nextLine().trim();
        if (QUIT_STRING.equalsIgnoreCase(searchValue)) {
            exitApplication();
        }
        return searchValue;
    }

    /**
     * Exit the search.
     */
    public void exitApplication() {
        System.exit(0);
    }

    /**
     * Print the searchable fields for User/Ticket/Organization
     */
    public void printSearchableFields() {
        PrintUtil.printFields(user.getDataName(), user.getFields());
        PrintUtil.printFields(ticket.getDataName(), ticket.getFields());
        PrintUtil.printFields(organization.getDataName(), organization.getFields());
        PrintUtil.printData(HYPHENS_LINE);
    }

}
