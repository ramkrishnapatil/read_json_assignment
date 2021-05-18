package pojo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import datautil.PrintUtil;

public class DeserializeDemoBean {


    public void run() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("users.json").getFile());
        String jsonFile = file.toString();

        try {
            ObjectMapper mapper = new ObjectMapper();
            File usersFile = Paths.get(jsonFile).toFile();
            UserPojo users = mapper.readValue(usersFile, UserPojo.class);
            users.toString();
        } catch (NullPointerException npe) {
            PrintUtil.printData("Unable to open jsonFile: " + jsonFile + " : " + npe.getMessage());
        } catch (JsonParseException jse) {
            PrintUtil.printData("Invalid data in jsonFile: " + jsonFile + " : "  + jse.getMessage());
        } catch (JsonMappingException jme) {
            PrintUtil.printData("Error while loading jsonFile: " + jsonFile + " : "  + jme.getMessage());
        } catch (IOException ioe) {
            PrintUtil.printData("Error while loading jsonFile: " + jsonFile + " : "  + ioe.getMessage());
        } catch (Exception exc) {
            PrintUtil.printData("Exception in loading jsonFile: " + jsonFile + " : "  + exc.getMessage());
        }
    }
}