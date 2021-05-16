package datautil;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import datastore.Data;

/**
 * The class is used to print the data.
 * This is wrapper to System.print. It will make easy to direct the output in future if application
 * decides to use any library.
 */
public final class PrintUtil {

    public static final String INPUT_PROMPT = "* ";
    private static final String FIELD_INDENT = " ";
    public static final String SEARCH_TABLES_OPTIONS = "\nSelect 1)Users or 2)Tickets or 3)Organizations or 4)Back to Main menu";
    public static final String SEARCH_FIELD_NAME = "\nEnter search term";
    public static final String SEARCH_FIELD_VALUE = "\nEnter search value";
    public static final String SEARCH_MENU_SPACE = "\t\t";
    public static final String SEARCH_OPTIONS = SEARCH_MENU_SPACE + "Select search options\n"
                    + SEARCH_MENU_SPACE + "* Press 1 to search Zendesk\n"
                    + SEARCH_MENU_SPACE + "* Press 2 to view list of searchable fields\n"
                    + SEARCH_MENU_SPACE + "* Type 'quit' to exit";
    public static final String ORGANIZATION_ID = "organization_id";
    public static final String ID_KEY = "_id";
    public static final String QUIT_STRING = "quit";
    public static final String INVALID_OPTION = "Invalid Option.";
    public static final String NEW_LINE = "\n";
    public static final String HYPHENS_LINE = "---------------------------------------------------------------------------------------------------------";

    public static void printGlobalSearchInformation() {
        printData("\nWelcome to Zendesk Search\n"
                        + "Type 'quit' to exit at any time, Press 'Enter' to continue\n");
    }

    public static void printSearchInformation() {
        printData(PrintUtil.SEARCH_OPTIONS);
    }

    public static void printSearchTablesInformation() {
        printData(PrintUtil.SEARCH_TABLES_OPTIONS);
    }

    public static void print(String printString) {
        System.out.print(printString);
    }

    public static void printData(String printString) {
        System.out.println(printString);
    }

    public static void printFields(String dataName, Set<String> fields) {
        printData("---------------------------------------------------------------------------------------------------------");
        printData("Search " + dataName + " with");
        for (String field : fields) {
            print(" |" + FIELD_INDENT + field);
        }
        print(PrintUtil.NEW_LINE);
    }

    public static void printResult(Data dataItem) {
        if (dataItem != null) {
            printData(NEW_LINE);
            dataItem.getFields().entrySet().forEach( entry -> {
                Object value = entry.getValue();
                System.out.printf("%-16s %s%n", entry.getKey(), value);
            });

        }
    }

    public static void printResult(Data dataItem, Set<String> configFields) {
        if (configFields == null || configFields.isEmpty() || dataItem == null) {
            printResult(dataItem);
            return;
        }

       //Sort by keys
        dataItem.getFields().entrySet().stream().forEach(field -> {
            if (configFields.contains(field.getKey())) {
                System.out.printf("%-16s %s%n", field.getKey(), field.getValue());
            }
        });
    }

    public static void printResults(List<Data> results) {
        if (results.isEmpty()) {
            printData("No results found");
            return;
        }
        results.removeIf(Objects::isNull);
        for (Data dataItem : results) {
            printResult(dataItem);
        }
    }

    public static void printResults(List<Data> results, Set<String> configFields) {
        if (results.isEmpty()) {
            printData("No results found");
            return;
        }
        results.removeIf(Objects::isNull);
        for (Data dataItem : results) {
            printResult(dataItem, configFields);
        }
    }

}
