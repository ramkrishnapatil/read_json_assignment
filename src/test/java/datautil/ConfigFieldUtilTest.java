package datautil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigFieldUtilTest {

    private ConfigFieldsUtil configFieldsUtil;

    @Before
    public void setUp() {
        configFieldsUtil = new ConfigFieldsUtil("configfields.json");
    }

    @After
    public void tearDown() {
        configFieldsUtil = null;
    }

    @Test
    public void getConfigFields() {
        Assert.assertEquals(3, configFieldsUtil.getConfigFields("Users").size());
        Assert.assertEquals(2, configFieldsUtil.getConfigFields("Tickets").size());
        Assert.assertEquals(0, configFieldsUtil.getConfigFields("Organizations").size());
    }

    @Test
    public void getConfigFieldsNotExist() {
        ConfigFieldsUtil configFieldsUtil1 = new ConfigFieldsUtil("configfields.json");
        Assert.assertNotNull(configFieldsUtil1.getConfigFields("Users"));
        Assert.assertNotNull(configFieldsUtil1.getConfigFields("Tickets"));
        Assert.assertNotNull(configFieldsUtil1.getConfigFields("Organizations"));
        Assert.assertEquals(0, configFieldsUtil1.getConfigFields("Organizations").size());
    }

}