package org.bcf.examplebot.nlu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.bcf.NLUModule;
import org.bcf.domain.Entity;
import org.bcf.domain.Intent;
import org.bcf.domain.Message;
import org.bcf.domain.StructuredMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class RasaNLU implements NLUModule {
    private URL endpoint;
    private JsonParser jsonParser = new JsonParser();

    public RasaNLU(URL endpoint) {
        this.endpoint = endpoint;
    }

    private String buildRequestBody(String text) {
        JsonObject obj = new JsonObject();
        obj.add("q", new JsonPrimitive(text));
        return obj.toString();
    }

    private StructuredMessage parseResponse(JsonObject json) {
        StructuredMessage msg = new StructuredMessage();
        msg.setText(json.get("text").getAsString());
        if (json.has("entities")) {
            for (JsonElement e : json.getAsJsonArray("entities")) {
                Entity entity = new Entity();
                JsonObject asJsonObject = e.getAsJsonObject();
                entity.setStartPosition(asJsonObject.get("start").getAsInt())
                        .setEndPosition(asJsonObject.get("start").getAsInt())
                        .setValue(asJsonObject.get("value").getAsString())
                        .setTypeId(asJsonObject.get("entity").getAsString())
                        .setSourceId(asJsonObject.get("extractor").getAsString());
                msg.getEntities().add(entity);
            }
        }
        if (json.has("intent")) {
            JsonObject jsonIntent = json.get("intent").getAsJsonObject();
            msg.setIntent(new Intent().setId(jsonIntent.get("name").getAsString()));
        }
        return msg;
    }

    @Override
    public StructuredMessage processMessage(Message message) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) endpoint.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "bot");
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(buildRequestBody(message.getText()));
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                StructuredMessage structuredMessage = parseResponse(jsonParser.parse(response.toString()).getAsJsonObject());
                structuredMessage.setOriginalMessage(message);
                return structuredMessage;
            } else {
                // TODO: exception
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
