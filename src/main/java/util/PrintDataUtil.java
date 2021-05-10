/*
 * Copyright (C) 2021 Thales ATM all rights reserved. This software is
 * the property of Thales ATM and may not be used, copied or disclosed
 * in any manner except under a licence agreement signed with Thales ATM.
 */
package util;

import java.util.List;
import java.util.Map;

public final class PrintDataUtil {

    public static void printResults(List<Map<String, Object>> results) {
        for (Map<String, Object> listItem : results) {
//            System.out.println();
//            for (Map.Entry<String, Object> entry : listItem.entrySet()) {
//                Object value = entry.getValue();
//                System.out.printf("%-16s %s%n", entry.getKey(), value);
//            }
            printResults(listItem);
        }
    }

    public static void printResults(Map<String, Object> results) {
        System.out.println();
        for (Map.Entry<String, Object> entry : results.entrySet()) {
            Object value = entry.getValue();
            System.out.printf("%-16s %s%n", entry.getKey(), value);
        }
    }

}
