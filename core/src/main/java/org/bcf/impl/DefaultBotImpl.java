/**
 * MIT License
 * <p>
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package org.bcf.impl;

import org.bcf.Bot;
import org.bcf.BotTransport;
import org.bcf.NLUModule;
import org.bcf.Skill;
import org.bcf.character.Character;
import org.bcf.domain.Message;
import org.bcf.domain.StructuredMessage;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class DefaultBotImpl implements Bot {
    private BotTransport transport;
    private NLUModule NLUModule;
    private Character character;

    @Override
    public void incomingMessage(Message message) {
        // Step 1. Process incoming message with NLU module
        StructuredMessage structuredMessage = this.NLUModule.processMessage(message);
        if (structuredMessage.getIntent() == null) {
            onUnrecognizedIntent(structuredMessage);
            return;
        }
        // Step 2. Find appropriate handler
        for (Skill skill : getSkillset()) {
            if (skill.canHandle(structuredMessage.getIntent().getId())) {
                skill.handle(structuredMessage, this);
            }
        }
    }

    @Override
    public void reply(Message message) {
        transport.reply(message);
    }

    @Override
    public BotTransport getTransport() {
        return transport;
    }

    public DefaultBotImpl setTransport(BotTransport transport) {
        this.transport = transport;
        return this;
    }

    @Override
    public org.bcf.NLUModule getNLUModule() {
        return NLUModule;
    }

    public DefaultBotImpl setNLUModule(org.bcf.NLUModule NLUModule) {
        this.NLUModule = NLUModule;
        return this;
    }

    @Override
    public Character getCharacter() {
        return character;
    }

    public DefaultBotImpl setCharacter(Character character) {
        this.character = character;
        return this;
    }

    @Override
    public void onUnrecognizedIntent(StructuredMessage message) {

    }

    @Override
    public void onProcessingError(StructuredMessage message, Exception e) {

    }

    @Override
    public void initialize() {
        if (!transport.isInitialized()) {
            transport.initialize();
        }
        transport.setOnMessageReceivedCallback(this::incomingMessage);
    }
}
