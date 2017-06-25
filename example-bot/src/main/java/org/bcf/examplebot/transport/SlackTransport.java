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
package org.bcf.examplebot.transport;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.bcf.BotTransport;
import org.bcf.MessageCallback;
import org.bcf.domain.Message;
import org.bcf.domain.Person;
import org.bcf.domain.Room;
import org.bcf.domain.TextMessage;

import javax.websocket.DeploymentException;
import java.io.IOException;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class SlackTransport implements BotTransport {
    private static final String MSG_TYPE_INCOMING_MESSAGE = "message";
    private String token;
    private RTMClient rtm;
    private Slack slack = new Slack();
    private MessageCallback messageReceivedCallback;
    private boolean initialized = false;

    private JsonParser jsonParser = new JsonParser();

    public SlackTransport(String token) {
        this.token = token;
    }

    @Override
    public void initialize() {
        try {
            rtm = slack.rtm(token);
            rtm.addMessageHandler((message) -> {
                JsonObject json = jsonParser.parse(message).getAsJsonObject();
                if (json.get("type") != null &&
                        MSG_TYPE_INCOMING_MESSAGE.equals(json.get("type").getAsString())) {
                    Message msg = buildMessageFromJson(json);
                    if (messageReceivedCallback != null) {
                        messageReceivedCallback.callback(msg);
                    }
                }
            });
            rtm.connect();
            initialized = true;
        } catch (IOException | DeploymentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeDestroy() {
        if (rtm != null) {
            try {
                rtm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setOnMessageReceivedCallback(MessageCallback callback) {
        messageReceivedCallback = callback;
    }


    @Override
    public void reply(Message message) {
        /**
         *
         * {
         "id": 1,
         "type": "message",
         "channel": "C024BE91L",
         "text": "Hello world"
         }
         */
        JsonObject json = new JsonObject();
        json.add("type", new JsonPrimitive("message"));
        json.add("text", new JsonPrimitive(message.getText()));
        json.add("channel", new JsonPrimitive(message.getRecipient().getId()));
        rtm.sendMessage(json.toString());
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    private Message buildMessageFromJson(JsonObject json) {
        TextMessage msg = new TextMessage();
        msg.setText(json.get("text").getAsString());
        msg.setSender(new Person(json.get("user").getAsString()));
        msg.setRoom(new Room(json.get("channel").getAsString()));
        // TODO: populate the rest
        return msg;
    }


}
