package datautil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ResourceLoaderTest {

    @Test
    public void testLoadFromJsonResource() {
        String jsonFile = "users.json";
        ClassLoader classLoader = getClass().getClassLoader();
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(classLoader.getResource(jsonFile));
        Assert.assertEquals(4, dataList.size());
    }

    @Test
    public void testExceptionInLoadFromJsonResource() {
        String jsonFile = "users1.json";
        ClassLoader classLoader = getClass().getClassLoader();
        List<Map<String, Object>> dataList = ResourceLoader.loadFromJsonResource(classLoader.getResource(jsonFile));
        Assert.assertEquals(0, dataList.size());
    }
}
