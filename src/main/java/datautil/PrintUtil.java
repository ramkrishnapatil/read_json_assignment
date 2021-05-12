package datautil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import data.Data;

public final class PrintUtil {
    public static final String INPUT_PROMPT = "* ";
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

    public static void printResult(Data dataItem) {
        System.out.println();
        //Sort by keys
        Map<String, Object> result = dataItem.getFields().entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        result.forEach((k, v) -> System.out.printf("%-16s %s%n", k, v));
    }

    public static void printResult(Data dataItem, Set<String> configFields) {
        if (configFields.isEmpty()) {
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
            System.out.println("No results found");
            return;
        }
        results.removeIf(Objects::isNull);
        for (Data dataItem : results) {
            printResult(dataItem);
        }
    }
    public static void printResults(List<Data> results, Set<String> configFields) {
        if (results.isEmpty()) {
            System.out.println("No results found");
            return;
        }
        results.removeIf(Objects::isNull);
        for (Data dataItem : results) {
            printResult(dataItem, configFields);
        }
    }

    public static void printGlobalSearchInformation() {
        PrintUtil.printData("\nWelcome to Zendesk Search\n"
                        + "Type 'quit' to exit at any time, Press 'Enter' to continue\n");
    }

    public static void printSearchInformation() {
        PrintUtil.printData(PrintUtil.SEARCH_OPTIONS);
    }

    public static void printSearchTablesInformation() {
        PrintUtil.printData(PrintUtil.SEARCH_TABLES_OPTIONS);
    }

    public static void print(String printString) {
        System.out.print(printString);
    }

    public static void printData(String printString) {
        System.out.println(printString);
    }
}
