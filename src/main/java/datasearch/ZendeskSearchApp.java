package datasearch;

import static datautil.PrintUtil.HYPENS_LINE;
import static datautil.PrintUtil.INPUT_PROMPT;
import static datautil.PrintUtil.NEW_LINE;
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
import datastore.Tickets;
import datastore.Users;
import datautil.PrintUtil;

public class ZendeskSearchApp {
    private final Users users;
    private final Tickets tickets;
    private final Organizations organizations;

    /**
     * Default constructor. This will load the data into java objects.
     */
    public ZendeskSearchApp() {
        users = new Users("users.json");
        tickets = new Tickets("tickets.json");
        organizations = new Organizations("organizations.json");
    }

    private static void printGlobalSearchInformation() {
        PrintUtil.printData("\nWelcome to Zendesk Search\n"
                        + "Type 'quit' to exit at any time, Press 'Enter' to continue\n");
    }

    private static void printSearchInformation() {
        PrintUtil.printData(PrintUtil.SEARCH_OPTIONS);
    }

    private static void printSearchTablesInformation() {
        PrintUtil.printData(PrintUtil.SEARCH_TABLES_OPTIONS);
    }

    public void searchData(Scanner scanner) {
        String menuOption = "";
        while (!PrintUtil.QUIT_STRING.equalsIgnoreCase(menuOption) && !"q".equalsIgnoreCase(menuOption)) {
            printSearchTablesInformation();
            System.out.print(PrintUtil.INPUT_PROMPT);
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
                PrintUtil.printData(" Invalid Option ");
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
            data = organizations;
            break;
        default:
            PrintUtil.printData("Invalid option");
            return;
        }

        if (!data.hasData()) {
            PrintUtil.printData(PrintUtil.NEW_LINE);
            PrintUtil.printData("Nothing to search!");
            return;
        }

        String searchField = promptForSearchableField(data, scanner);
        String searchValue = promptForSearchValue(scanner);
        List<Data> results = data.search(searchField, searchValue);

        searchRelatedDatastore(menuOption, results);
    }

    /**
     * This function will search the relation
     * @param menuOption Selected menu option
     * @param results Results searched
     */
    private void searchRelatedDatastore(String menuOption, List<Data> results) {
        if (results.isEmpty()) {
            PrintUtil.printResults(results);
            return;
        }
        //If the organization_id and tickets/users id are same then we will need two sets
        Set<String> idValues = new HashSet<>();
        switch (menuOption) {
        case "1":
        case "2":
            // Currently do not the relation of users and tickets so not implemented
            // Otherwise it will be case 2 will be separate.
            results.forEach(result -> {
                if (result.getFields().containsKey(PrintUtil.ORGANIZATION_ID)) {
                    idValues.add(result.getFieldValue(PrintUtil.ORGANIZATION_ID).toString());
                }
            });
            PrintUtil.printResults(results);
            if (!idValues.isEmpty()) {
                PrintUtil.printResults(organizations.searchDataStoreById(idValues));
            }
            break;
        case "3":
            results.forEach(result -> idValues.add(result.getId()));
            PrintUtil.printResults(results);
            if (!idValues.isEmpty()) {
                PrintUtil.printResults(users.searchDataStoreByField(PrintUtil.ORGANIZATION_ID, idValues));
                PrintUtil.printResults(tickets.searchDataStoreByField(PrintUtil.ORGANIZATION_ID, idValues));
            }
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
            PrintUtil.printData(SEARCH_FIELD_NAME);
            System.out.print(INPUT_PROMPT);
            searchableField = scanner.nextLine().trim();
            if (QUIT_STRING.equalsIgnoreCase(searchableField)) {
                closeTheApplication();
            }

            validField = data.isSearchableField(searchableField);
            if (!validField) {
                PrintUtil.printData("Invalid field, try again. Valid fields are : ");
                data.printSearchableFields();
                PrintUtil.printData(HYPENS_LINE);
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
        PrintUtil.printData(SEARCH_FIELD_VALUE);
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
        PrintUtil.printData(NEW_LINE);

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
                    PrintUtil.printData(HYPENS_LINE);
                    break;
                default:
                    break;
                }
            }
        }
    }
}
