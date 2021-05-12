package search;

import static datautil.PrintUtil.HYPHENS_LINE;
import static datautil.PrintUtil.INPUT_PROMPT;
import static datautil.PrintUtil.QUIT_STRING;
import static datautil.PrintUtil.SEARCH_FIELD_NAME;
import static datautil.PrintUtil.SEARCH_FIELD_VALUE;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.Data;
import datastore.DataStore;
import datastore.Organizations;
import datautil.ConfigFieldsUtil;
import datastore.Tickets;
import datastore.Users;
import datautil.PrintUtil;

public class ZendeskSearchApp {
    private final Users users;
    private final Tickets tickets;
    private final Organizations organizations;
    private final ConfigFieldsUtil configFieldsUtil;

    /**
     * Default constructor. This will load the data into java objects.
     */
    public ZendeskSearchApp() {
        users = new Users("users.json");
        tickets = new Tickets("tickets.json");
        organizations = new Organizations("organizations.json");
        configFieldsUtil = new ConfigFieldsUtil("configfields.json");
    }

    public void searchData(String menuOption, Scanner scanner) {
        DataStore data;
        switch (menuOption) {
        case "1":
            data = users;
            break;
        case "2":
            data = tickets;
            break;
        case "3":
            data = organizations;
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
     * This function will search the relation
     * @param menuOption Selected menu option
     * @param results Results searched
     */
    private void searchFromRelatedDatastore(String menuOption, List<Data> results) {
        if (results.isEmpty()) {
            PrintUtil.printResults(results);
            return;
        }
        //If the organization_id and tickets/users id are same then we will need two sets
        Set<String> idValues = new HashSet<>();
        switch (menuOption) {
        case "1":
        case "2":
            // Currently do not know the relation of users and tickets so not implemented
            // Otherwise it case 2 will be separate.
            results.forEach(result -> {
                PrintUtil.printResult(result);
                if (result.getFields().containsKey(PrintUtil.ORGANIZATION_ID)) {
                    String orgId = result.getFieldValue(PrintUtil.ORGANIZATION_ID).toString();
                    PrintUtil.printResult(organizations.searchDataStoreById(orgId), configFieldsUtil.getConfigFields(organizations.getDataName()));
                }
            });
            break;
        case "3":
            results.forEach(result -> {
                PrintUtil.printResult(result);
                if (result.getId() != null && !result.getId().isEmpty()) {
                    PrintUtil.printData("Printing " + users.getDataName() + " for id : " + result.getId());
                    PrintUtil.printResults(users.searchDataStoreByField(PrintUtil.ORGANIZATION_ID, result.getId()), configFieldsUtil.getConfigFields(users.getDataName()));
                    PrintUtil.printData("Printing " + tickets.getDataName() + " for id : " + result.getId());
                    PrintUtil.printResults(tickets.searchDataStoreByField(PrintUtil.ORGANIZATION_ID, result.getId()), configFieldsUtil.getConfigFields(tickets.getDataName()));
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
                data.printSearchableFields();
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

    public void exitApplication() {
        System.exit(0);
    }

    public void printSearchableFields() {
        users.printSearchableFields();
        tickets.printSearchableFields();
        organizations.printSearchableFields();
        PrintUtil.printData(HYPHENS_LINE);
    }

}
