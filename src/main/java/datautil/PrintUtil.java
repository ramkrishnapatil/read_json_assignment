package datautil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import data.Data;

public final class PrintUtil {

//    public static void printResults(List<Map<String, Object>> results) {
//        for (Map<String, Object> listItem : results) {
////            System.out.println();
////            for (Map.Entry<String, Object> entry : listItem.entrySet()) {
////                Object value = entry.getValue();
////                System.out.printf("%-16s %s%n", entry.getKey(), value);
////            }
//            printResults(listItem);
//        }
//    }

    public static void printResults(Map<String, Object> results) {
        System.out.println();
        for (Map.Entry<String, Object> entry : results.entrySet()) {
            Object value = entry.getValue();
            System.out.printf("%-16s %s%n", entry.getKey(), value);
        }
    }

    public static void printResults(Data dataItem) {
        System.out.println();
        //        for (Map.Entry<String, Object> entry : dataItem.getFields().entrySet()) {
        //        Object value = entry.getValue();
        //Sort by keys
        Map<String, Object> result = dataItem.getFields().entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        result.forEach((k, v) -> System.out.printf("%-16s %s%n", k, v));
}

    public static void printResults(List<Data> results) {
        if (results.isEmpty()) {
            System.out.println("No results found");
            return;
        }
        for (Data dataItem : results) {
            printResults(dataItem);
        }
    }

}
