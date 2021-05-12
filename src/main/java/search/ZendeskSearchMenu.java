package search;

import static datautil.PrintUtil.INPUT_PROMPT;
import static datautil.PrintUtil.NEW_LINE;
import static datautil.PrintUtil.QUIT_STRING;

import java.util.Scanner;

import datautil.PrintUtil;

public class ZendeskSearchMenu {

    private ZendeskSearchApp searchApp;

    public ZendeskSearchMenu() {
        searchApp = new ZendeskSearchApp();
    }

    public void selectSearchOption(Scanner scanner) {
        String menuOption = "";
        while (!PrintUtil.QUIT_STRING.equalsIgnoreCase(menuOption) && !"q".equalsIgnoreCase(menuOption)) {
            PrintUtil.printSearchTablesInformation();
            PrintUtil.print(PrintUtil.INPUT_PROMPT);
            menuOption = scanner.nextLine().trim();

            switch (menuOption) {
            case "1":
            case "2":
            case "3":
                searchApp.searchData(menuOption, scanner);
                break;
            case "4":
                return;
            default:
                PrintUtil.printData(PrintUtil.INVALID_OPTION);
                break;
            }
        }
        searchApp.exitApplication();
    }

    /**
     * Run the Application
     */
    public void run() {
        PrintUtil.printData(NEW_LINE);

        try (Scanner scanner = new Scanner(System.in)) {
            String menuOption = "";
            while (!QUIT_STRING.equalsIgnoreCase(menuOption)) {
                PrintUtil.printGlobalSearchInformation();
                PrintUtil.printSearchInformation();
                PrintUtil.print(INPUT_PROMPT);
                menuOption = scanner.nextLine().trim();

                switch (menuOption) {
                case "1":
                    selectSearchOption(scanner);
                    break;
                case "2":
                    searchApp.printSearchableFields();
                    break;
                default:
                    break;
                }
            }
        }
    }
}
