package datautil;

import java.io.File;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ResourceLoaderTest {

    @Test
    public void testLoadFromJsonResource() {
        String jsonFile = "users.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File fileName = new File(classLoader.getResource(jsonFile).getFile());
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(fileName.toString());
        Assert.assertEquals(4, dataList.size());
    }

    @Test(expected = NullPointerException.class)
    public void testExceptionInLoadFromJsonResource() {
        String jsonFile = "users1.json";
        ClassLoader classLoader = getClass().getClassLoader();
        new File(classLoader.getResource(jsonFile).getFile());
    }
}
