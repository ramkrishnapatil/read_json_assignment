package datautil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import datastore.Data;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrintUtilTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void printResult() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("_id", 1);
        dataMap.put("name", "Merry");
        dataMap.put("alias", "Cherry");
        Data data = new Data(dataMap);
        PrintUtil.printResult(data);
        String printString = "_id              1\n" + "alias            Cherry\n" + "name             Merry";
        Assert.assertEquals(printString.trim(), outContent.toString().trim().replaceAll("\r\n", "\n"));
    }

    @Test
    public void printResultWithConfigField() {
        Map<String, Object> dataMap = new HashMap<>();
        String id = "_id";
        String name = "name";
        String alias = "alias";
        dataMap.put(id, 1);
        dataMap.put(name, "Merry");
        dataMap.put(alias, "Cherry");
        Data data = new Data(dataMap);
        Set<String> configFields = new HashSet<>();
        configFields.add(name);
        configFields.add(alias);
        PrintUtil.printResult(data, configFields);
        outContent.toString().trim().replaceAll("\r\n", "\n");
        Assert.assertEquals("name             Merry\n" + "alias            Cherry", outContent.toString().trim().replaceAll("\r\n", "\n"));
    }

}
