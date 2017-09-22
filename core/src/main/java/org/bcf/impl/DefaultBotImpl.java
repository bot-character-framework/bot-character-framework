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
package org.bcf.impl;

import org.apache.commons.collections4.ListUtils;
import org.bcf.*;
import org.bcf.character.Character;
import org.bcf.character.PhraseFormatter;
import org.bcf.character.impl.SimpleFormatter;
import org.bcf.domain.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class DefaultBotImpl<P extends Enum<P>> implements Bot<P> {
    private BotTransport transport;
    private NLUModule NLUModule;
    private Character<P> character;
    private ConversationSessionStorage<P> sessionStorage;
    private Map<String, Skill<? extends Bot<P>, P>> name2skill = null;
    private PhraseFormatter phraseFormatter = new SimpleFormatter();

    @Override
    public void incomingMessage(Message message) {
        // Step 0. Create session for message
        ConversationSession<P> session = buildSessionForMessage(message);
        // Step 1. Process incoming message with NLU module
        StructuredMessage structuredMessage = this.NLUModule.processMessage(message);
        if (structuredMessage.getIntent() == null) {
            onUnrecognizedIntent(structuredMessage, session);
            return;
        }
        try {
            getSessionStorage().lock(session);
            if (!shouldHandleMessage(structuredMessage, session)) {
                return;
            }
            // Step 2. Try to match expectations
            ConversationExpectation expectation = session.popExpectation();
            while (expectation != null) {
                Skill<? extends Bot<P>, P> matchedSkill = findSkillMatchingExpectation(expectation, structuredMessage);
                if (matchedSkill != null) {
                    StructuredMessage nextMessage = matchedSkill.handleExpectation(expectation, structuredMessage, session);
                    session.clearExpectations();
                    if (nextMessage != null) {
                        structuredMessage = nextMessage;
                        break;      // If expectation instructs us which message should be processed next
                                    // - go ahead to the processing block
                    } else {
                        return;     // Otherwise - terminate and wait for the next input from user
                    }
                }
                expectation = session.popExpectation();
            }
            // Step 3. Find appropriate handler
            for (Skill<? extends Bot<P>, P> skill : getSkillset()) {
                if (skill.canHandle(structuredMessage.getIntent().getId())) {
                    skill.handleMessage(structuredMessage, session);
                    return;
                }
            }
        } catch(Exception e) {
            onProcessingError(structuredMessage, session, e);
        } finally {
            getSessionStorage().unlock(session);
        }
    }

    /**
     * Method will be called before processing message. If return FALSE message will be ignored.
     * It might be overridden to introduce some filtering behaviour.
     * @param structuredMessage
     * @param session
     * @return
     */
    protected boolean shouldHandleMessage(StructuredMessage structuredMessage, ConversationSession<P> session){
        return true;
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
    public void onUnrecognizedIntent(StructuredMessage message, ConversationSession<P> session) {

    }

    @Override
    public void onProcessingError(StructuredMessage message, ConversationSession<P> session, Exception e) {

    }

    @Override
    public ConversationSessionStorage<P> getSessionStorage() {
        return sessionStorage;
    }

    public DefaultBotImpl setSessionStorage(ConversationSessionStorage<P> sessionStorage) {
        this.sessionStorage = sessionStorage;
        return this;
    }

    public PhraseFormatter getPhraseFormatter() {
        return phraseFormatter;
    }

    public DefaultBotImpl setPhraseFormatter(PhraseFormatter phraseFormatter) {
        this.phraseFormatter = phraseFormatter;
        return this;
    }

    @Override
    public void initialize() {
        if (getSessionStorage() == null) {
            setSessionStorage(new InMemoryConversationSessionStorage<>(this));
        }
        if (!transport.isInitialized()) {
            transport.initialize();
        }
        transport.setOnMessageReceivedCallback(this::incomingMessage);
    }

    @Override
    public ConversationSession<P> buildSessionForMessage(Message message) {
        String sessionId = message.getRoom().getId();
        if (getSessionStorage().sessionExists(sessionId)) {
            return getSessionStorage().getSession(sessionId);
        }
        DefaultConversationSessionImpl<P> session = new DefaultConversationSessionImpl<>(this, sessionId);
        session.setParticipant(message.getSender());
        session.setResponseTarget(message.getRoom());
        getSessionStorage().persistSession(session);
        return session;
    }

    protected Skill<? extends Bot<P>, P> findSkillMatchingExpectation(ConversationExpectation expectation,
                                                                      StructuredMessage structuredMessage) {
        // Check if we have catch all expectation
        if (expectation.isCatchAll()) {
            return getNametoSkill().get(expectation.getTargetName());
        }
        // Check if message matches intent
        if (structuredMessage.getIntent() != null && expectation.getExpectedIntents().contains(structuredMessage.getIntent().getId())) {
            return getNametoSkill().get(expectation.getTargetName());
        }
        // Check if message matches one of expected entities
        List<String> messageEntities = structuredMessage.getEntities()
                .stream().map(Entity::getTypeId).collect(Collectors.toList());
        if (!ListUtils.intersection(expectation.getExpectedEntities(), messageEntities).isEmpty()) {
            return getNametoSkill().get(expectation.getTargetName());
        }
        return null;
    }

    private Map<String, Skill<? extends Bot<P>, P>> getNametoSkill() {
        if (name2skill == null) {
            name2skill = new HashMap<>();
            List<Skill<? extends Bot<P>, P>> skillset = getSkillset();
            if (skillset != null && skillset.size() != name2skill.size()) {
                name2skill.clear();
                skillset.forEach(pSkill -> name2skill.put(pSkill.getClass().getCanonicalName(), pSkill));
            }
            return name2skill;
        } else {
            return name2skill;
        }
    }


}
