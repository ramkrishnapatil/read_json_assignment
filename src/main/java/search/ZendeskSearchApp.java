package search;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import model.Data;
import model.Organization;
import model.Ticket;
import model.User;
import util.PrintDataUtil;

public class ZendeskSearchApp {
    private static final String INPUT_PROMPT = "* ";
    private static final String SEARCH_TABLES_OPTIONS = "\nSelect 1)Users or 2)Tickets or 3)Organizations";
    private static final String SEARCH_FIELD_NAME = "Enter search term";
    private static final String SEARCH_FIELD_VALUE = "Enter search value";
    private static final String SEARCH_MENU_SPACE = "\t\t";
    private static final String SEARCH_OPTIONS = SEARCH_MENU_SPACE + "Select search options\n"
                    + SEARCH_MENU_SPACE + "* Press 1 to search Zendesk\n"
                    + SEARCH_MENU_SPACE + "* Press 2 to view list of searchable fields\n"
                    + SEARCH_MENU_SPACE + "* Type 'quit' to exit";

    private final Data users;
    private final Data tickets;
    private final Data organizations;

    /**
     * Default constructor. This will load the data into objects.
     */
    public ZendeskSearchApp() {
        users = new User("users.json");
        tickets = new Ticket("tickets.json");
        organizations = new Organization("organizations.json");
    }

    private static void printGlobalSearchInformation() {
        System.out.println("Welcome to Zendesk Search\n"
                        + "Type 'quit' to exit at any time, Press 'Enter' to continue\n\n\n");
    }

    private static void printSearchInformation() {
        System.out.println(SEARCH_OPTIONS);
    }

    private static void printSearchTablesInformation() {
        System.out.println(SEARCH_TABLES_OPTIONS);
    }

    public void searchData(Scanner scanner) {
        String menuOption = "";
        while (!"quit".equalsIgnoreCase(menuOption) && !"q".equalsIgnoreCase(menuOption)) {
            printSearchTablesInformation();
            System.out.print(INPUT_PROMPT);
            menuOption = scanner.nextLine().trim();

            switch (menuOption) {
            case "1":
            case "2":
            case "3":
                search(menuOption, scanner);
                break;
            default:
                System.out.println();
                System.out.println("Invalid option");
                break;
            }
        }
        System.exit(0);
    }

    private void search(String menuOption, Scanner scanner) {
        Data data;
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
        List<Map<String, Object>> results = data.search(searchField, searchValue);

        PrintDataUtil.printResults(results);

        Set<String> idValues = new HashSet<>();
        switch (menuOption) {
        case "1":
            results.forEach(result -> idValues.add(result.get("organization_id").toString()));
//            tickets.searchIdAndPrintRecord(idValues);
            organizations.searchIdAndPrintRecord(idValues);
            break;
        case "2":
            // Do not know the relation of tickets
            results.forEach( result -> System.out.println(result.get("organization_id").toString()));
//            users.searchIdAndPrintRecord(idValues);
            organizations.searchIdAndPrintRecord(idValues);
            break;
        case "3":
        default:
//            users.searchIdAndPrintRecord(searchField, searchValue);
//            tickets.searchIdAndPrintRecord(searchField, searchValue);
            break;
        }

    }

    private static String promptForSearchableField(Data data, Scanner scanner) {
        String searchableField;

        boolean validField;
        do {
            System.out.println();
            System.out.println(SEARCH_FIELD_NAME);
            System.out.print(INPUT_PROMPT);
            searchableField = scanner.nextLine().trim();

            validField = data.isSearchableField(searchableField);
            if (!validField) {
                System.out.println("Invalid field, try again. Valid fields are");
                data.printSearchableFields();
            }
        } while (!validField);

        return searchableField;
    }

    private static String promptForSearchValue(Scanner scanner) {
        System.out.println();
        System.out.println(SEARCH_FIELD_VALUE);
        System.out.print(INPUT_PROMPT);
        return scanner.nextLine().trim();
    }

    /**
     * Application execution begins here.
     */
    public void run() {
        System.out.println();
        printGlobalSearchInformation();

        try (Scanner scanner = new Scanner(System.in)) {
            String menuOption = "";
            while (!"quit".equalsIgnoreCase(menuOption)) {
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
                    System.out.println();
                    System.out.println("Invalid option");
                    break;
                }
            }
        }
        System.exit(0);
    }
}
