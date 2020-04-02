package pl.widulinski.webcrawlertool.parseToJSON;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.springframework.util.Assert;


public class JSONParsing {

    void createJSON(String string) throws JsonSyntaxException {

        String json = "{ \"name\": \"Baeldung\", \"java\": true }";
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        Assert.isTrue(jsonObject.isJsonObject());
        Assert.isTrue(jsonObject.get("name").getAsString().equals("Baeldung"));
        Assert.isTrue(jsonObject.get("java").getAsBoolean() == true);

    }
}
