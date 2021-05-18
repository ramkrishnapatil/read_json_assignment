package search;

import static datautil.PrintUtil.HYPHENS_LINE;
import static datautil.PrintUtil.INPUT_PROMPT;
import static datautil.PrintUtil.QUIT_STRING;
import static datautil.PrintUtil.SEARCH_FIELD_NAME;
import static datautil.PrintUtil.SEARCH_FIELD_VALUE;

import java.util.Scanner;

import datastore.DataStore;
import datastore.DatastoreManager;
import datautil.PrintUtil;

/**
 * This class creates Users/Tickets/Organizations from the  json files.
 * This classes are used to search/display data.
 */
public class ZendeskSearchApp {
    private final DataStore user;
    private final DataStore ticket;
    private final DataStore organization;
    private final DatastoreManager datastoreManager;

    /**
     * Default constructor. This will load the data into java objects.
     */
    public ZendeskSearchApp() {
        datastoreManager = new DatastoreManager();
        user = datastoreManager.getUser();
        ticket = datastoreManager.getTicket();
        organization = datastoreManager.getOrganization();
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
//            PrintUtil.printData(userPojo.toString());
            data = user;
            break;
        case "2":
            data = ticket;
            break;
        case "3":
//            PrintUtil.printData(organizationPojo.toString());
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
        datastoreManager.searchAndPrintData(data.getDataName(), searchField, searchValue);
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
