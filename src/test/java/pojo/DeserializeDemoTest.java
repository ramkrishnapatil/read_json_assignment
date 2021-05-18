package pojo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import datautil.PrintUtil;
import org.junit.Test;

public class DeserializeDemoTest {

    @Test
    public void testPojoDemo() throws JsonProcessingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileUrl = classLoader.getResource("users.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            mapper.findAndRegisterModules();
            String jsonFile = new File(fileUrl.getFile()).toString();
            File usersFile = Paths.get(jsonFile).toFile();
            UserPojo[] userPojo = mapper.readValue(usersFile, UserPojo[].class);
            System.out.println(userPojo.length);
            System.out.println(userPojo[0]);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            PrintUtil.printData("Unable to open jsonFile: " + fileUrl + " : " + npe.getMessage());
        } catch (Exception exc) {
            PrintUtil.printData("Exception in loading jsonFile: " + fileUrl + " : "  + exc.getMessage());
        }
    }

    @Test
    public void testPojoFields() throws JsonProcessingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileUrl = classLoader.getResource("users.json");

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false);
            mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
            mapper.findAndRegisterModules();
            String jsonFile = new File(fileUrl.getFile()).toString();
            File usersFile = Paths.get(jsonFile).toFile();
            UserPojo[] userPojo = mapper.readValue(usersFile, UserPojo[].class);
            for (UserPojo user : userPojo) {
                System.out.println(user);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            PrintUtil.printData("Unable to open jsonFile: " + fileUrl + " : " + npe.getMessage());
        } catch (Exception exc) {
            PrintUtil.printData("Exception in loading jsonFile: " + fileUrl + " : "  + exc.getMessage());
        }
    }

}