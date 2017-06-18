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
import org.bcf.domain.ConversationSession;
import org.bcf.domain.DefaultConversationSessionImpl;
import org.bcf.domain.Message;
import org.bcf.domain.StructuredMessage;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class DefaultBotImpl<P extends Enum<P>> implements Bot<P> {
    private BotTransport transport;
    private NLUModule NLUModule;
    private Character<P> character;

    @Override
    public void incomingMessage(Message message) {
        // Step 0. Create session for message
        ConversationSession<P> session = buildSessionForMessage(message);
        // Step 1. Process incoming message with NLU module
        StructuredMessage structuredMessage = this.NLUModule.processMessage(message);
        if (structuredMessage.getIntent() == null) {
            onUnrecognizedIntent(structuredMessage);
            return;
        }
        // Step 2. Find appropriate handler
        for (Skill<? extends Bot<P>, P> skill : getSkillset()) {
            if (skill.canHandle(structuredMessage.getIntent().getId())) {
                skill.handle(structuredMessage, session);
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

    public DefaultBotImpl<P> setTransport(BotTransport transport) {
        this.transport = transport;
        return this;
    }

    @Override
    public org.bcf.NLUModule getNLUModule() {
        return NLUModule;
    }

    public DefaultBotImpl<P> setNLUModule(org.bcf.NLUModule NLUModule) {
        this.NLUModule = NLUModule;
        return this;
    }

    @Override
    public Character<P> getCharacter() {
        return character;
    }

    public DefaultBotImpl<P> setCharacter(Character<P> character) {
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

    @Override
    public ConversationSession<P> buildSessionForMessage(Message message) {
        // TODO: Proper implementation required
        DefaultConversationSessionImpl<P> session = new DefaultConversationSessionImpl<>(this);
        session.setId(message.getRoom().getId());
        session.setParticipant(message.getSender());
        session.setResponseTarget(message.getRoom());
        return session;
    }
}
