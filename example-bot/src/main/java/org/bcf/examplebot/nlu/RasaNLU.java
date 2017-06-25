/**
 * MIT License
 *
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
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
    private String modelName = "default";

    public RasaNLU(URL endpoint) {
        this.endpoint = endpoint;
    }

    private String buildRequestBody(String text) {
        JsonObject obj = new JsonObject();
        obj.add("q", new JsonPrimitive(text));
        obj.add("model", new JsonPrimitive(getModelName()));
        return obj.toString();
    }

    private StructuredMessage parseResponse(JsonObject json) {
        StructuredMessage msg = new StructuredMessage();
        msg.setText(json.get("text").getAsString());
        if (json.has("entities")) {
            for (JsonElement e : json.getAsJsonArray("entities")) {
                Entity entity = new Entity();
                JsonObject asJsonObject = e.getAsJsonObject();
                JsonElement value = asJsonObject.get("value");
                entity.setStartPosition(asJsonObject.get("start").getAsInt())
                        .setEndPosition(asJsonObject.get("start").getAsInt())
                        .setValue(value.isJsonPrimitive() ? value.getAsString() : value.toString())
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

    public String getModelName() {
        return modelName;
    }

    public RasaNLU setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }
}
