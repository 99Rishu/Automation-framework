package utils;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Read json files and retrieve data 

public class JsonUtils {

    static String filePath = "json/config.json";

    // Read any json file
    public static JSONObject readJsonFile(String path) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader reader = new FileReader(path);
        return (JSONObject) jsonParser.parse(reader);
    }

    public static String getConfigValue(String key) throws IOException, ParseException {
        JSONObject config = readJsonFile(filePath);
        return (String) config.get(key);
    }

    public static String getLocator(String key) throws IOException, ParseException {
        JSONObject config = readJsonFile(filePath);
        JSONObject locators = (JSONObject) config.get("locators");
        return (String) locators.get(key);
    }

    public static Object[][] getLoginData() throws IOException, ParseException {
        JSONObject config = readJsonFile(filePath);
        JSONArray userloginsArray = (JSONArray) config.get("userlogins");

        Object[][] data = new Object[userloginsArray.size()][2]; // Ensure proper format
        for (int i = 0; i < userloginsArray.size(); i++) {
            JSONObject users = (JSONObject) userloginsArray.get(i);
            data[i][0] = (String) users.get("username");
            data[i][1] = (String) users.get("password");
        }
        return data;
    }
}
