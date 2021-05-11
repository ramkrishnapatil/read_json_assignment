package datasearch;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import data.Data;
import datastore.DataStore;
import datastore.Organizations;
import datastore.Tickets;
import datastore.Users;
import datautil.PrintUtil;

public class ZendeskSearchApp {
    private static final String INPUT_PROMPT = "* ";
    private static final String SEARCH_TABLES_OPTIONS = "\nSelect 1)Users or 2)Tickets or 3)Organizations or 4)Back to Main menu";
    private static final String SEARCH_FIELD_NAME = "Enter search term";
    private static final String SEARCH_FIELD_VALUE = "Enter search value";
    private static final String SEARCH_MENU_SPACE = "\t\t";
    private static final String SEARCH_OPTIONS = SEARCH_MENU_SPACE + "Select search options\n"
                    + SEARCH_MENU_SPACE + "* Press 1 to search Zendesk\n"
                    + SEARCH_MENU_SPACE + "* Press 2 to view list of searchable fields\n"
                    + SEARCH_MENU_SPACE + "* Type 'quit' to exit";
    private static final String ORGANIZATION_ID = "organization_id";
    private static final String QUIT_STRING = "quit";

    private final Users users;
    private final Tickets tickets;
    private final Organizations organizations;

    /**
     * Default constructor. This will load the data into objects.
     */
    public ZendeskSearchApp() {
        users = new Users("users.json");
        tickets = new Tickets("tickets.json");
        organizations = new Organizations("organizations.json");
    }

    private static void printGlobalSearchInformation() {
        System.out.println("Welcome to Zendesk Search\n"
                        + "Type 'quit' to exit at any time, Press 'Enter' to continue\n");
    }

    private static void printSearchInformation() {
        System.out.println(SEARCH_OPTIONS);
    }

    private static void printSearchTablesInformation() {
        System.out.println(SEARCH_TABLES_OPTIONS);
    }

    public void searchData(Scanner scanner) {
        String menuOption = "";
        while (!QUIT_STRING.equalsIgnoreCase(menuOption) && !"q".equalsIgnoreCase(menuOption)) {
            printSearchTablesInformation();
            System.out.print(INPUT_PROMPT);
            menuOption = scanner.nextLine().trim();

            switch (menuOption) {
            case "1":
            case "2":
            case "3":
                search(menuOption, scanner);
                break;
            case "4":
                return;
            default:
                System.out.println(" Invalid Option ");
                break;
            }
        }
        closeTheApplication();
    }

    private void search(String menuOption, Scanner scanner) {
        DataStore data;
        switch (menuOption) {
        case "1":
            data = users;
            break;
        case "2":
            data = tickets;
            break;
        case "3":
        default:
            data = organizations;
            break;
        }

        if (!data.hasData()) {
            System.out.println();
            System.out.println("Nothing to search!");
            return;
        }

        String searchField = promptForSearchableField(data, scanner);
        String searchValue = promptForSearchValue(scanner);
        List<Data> results = data.search(searchField, searchValue);

        PrintUtil.printResults(results);
        if (!results.isEmpty()) {
            searchRelatedDatastore(menuOption, results);
        }
    }

    /**
     * This function will search the relation
     * @param menuOption Selected menu option
     * @param results Results searched
     */
    private void searchRelatedDatastore(String menuOption, List<Data> results) {
        //If the organization_id and tickets/users id are same then we will need two sets
        Set<String> idValues = new HashSet<>();
        switch (menuOption) {
        case "1":
            results.forEach(result -> idValues.add(result.getFieldValue(ORGANIZATION_ID).toString()));
            //            tickets.searchIdAndPrintRecord(idValues);
            PrintUtil.printResults(organizations.searchDataStore(idValues));
            break;
        case "2":
            results.forEach(result -> idValues.add(result.getFieldValue(ORGANIZATION_ID).toString()));
            // Do not know the relation of tickets
            //            users.searchIdAndPrintRecord(idValues);
            PrintUtil.printResults(organizations.searchDataStore(idValues));
            break;
        case "3":
            //            users.searchIdAndPrintRecord(searchField, searchValue);
            //            tickets.searchIdAndPrintRecord(searchField, searchValue);
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
    private static String promptForSearchableField(DataStore data, Scanner scanner) {
        String searchableField;

        boolean validField;
        do {
            System.out.println();
            System.out.println(SEARCH_FIELD_NAME);
            System.out.print(INPUT_PROMPT);
            searchableField = scanner.nextLine().trim();
            if (QUIT_STRING.equalsIgnoreCase(searchableField)) {
                closeTheApplication();
            }

            validField = data.isSearchableField(searchableField);
            if (!validField) {
                System.out.println("Invalid field, try again. Valid fields are : ");
                data.printSearchableFields();
            }
        } while (!validField);

        return searchableField;
    }

    /**
     * Prompt for search value
     * @param scanner
     * @return search field value
     */
    private static String promptForSearchValue(Scanner scanner) {
        System.out.println();
        System.out.println(SEARCH_FIELD_VALUE);
        System.out.print(INPUT_PROMPT);
        String searchValue = scanner.nextLine().trim();
        if (QUIT_STRING.equalsIgnoreCase(searchValue)) {
            closeTheApplication();
        }
        return searchValue;
    }

    private static void closeTheApplication() {
        System.exit(0);
    }

    /**
     * Application execution begins here.
     */
    public void run() {
        System.out.println();

        try (Scanner scanner = new Scanner(System.in)) {
            String menuOption = "";
            while (!QUIT_STRING.equalsIgnoreCase(menuOption)) {
                printGlobalSearchInformation();
                printSearchInformation();
                System.out.print(INPUT_PROMPT);
                menuOption = scanner.nextLine().trim();

                switch (menuOption) {
                case "1":
                    searchData(scanner);
                    break;
                case "2":
                    users.printSearchableFields();
                    tickets.printSearchableFields();
                    organizations.printSearchableFields();
                    break;
                default:
                    break;
                }
            }
        }
    }
}
