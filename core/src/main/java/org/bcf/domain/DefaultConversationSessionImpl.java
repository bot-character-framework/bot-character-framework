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
package org.bcf.domain;

import org.bcf.Bot;
import org.bcf.Skill;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class DefaultConversationSessionImpl<P extends Enum<P>> implements ConversationSession<P> {
    private String id;
    private List<Person> participants;
    private Person participant;
    private ConversationParticipant responseTarget;
    private Stack<ConversationExpectation> expectations = new Stack<>();
    private ConversationContext context;
    private transient Bot<P> bot;

    public DefaultConversationSessionImpl(Bot<P> bot, String id) {
        this.bot = bot;
        this.setId(id);
        this.context = new DefaultConversationContext(id);
    }

    @Override
    public String getId() {
        return id;
    }

    private DefaultConversationSessionImpl setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public List<Person> getParticipants() {
        return participants;
    }

    public DefaultConversationSessionImpl setParticipants(List<Person> participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public Person getParticipant() {
        return participant;
    }

    @Override
    public ConversationExpectation popExpectation() {
        if (expectations.isEmpty()) {
            return null;
        }
        return expectations.pop();
    }

    @NotNull
    @Override
    public ConversationContext getContext() {
        return context;
    }

    @Override
    public void clearExpectations() {
        expectations.clear();
    }

    @Override
    public boolean persisted() {
        return getId() != null;
    }

    @Override
    public void reply(Message message) {
        if (message.getRecipient() == null) {
            message.setRecipient(getResponseTarget());
        }
        bot.reply(message);
    }

    @Override
    public void reply(P phrase, Map<String, String> context) {
        TextMessage message = new TextMessage(bot.getCharacter().getLine(phrase, context).getText());
        message.setRecipient(getResponseTarget());
        bot.reply(message);
    }

    @Override
    public ConversationExpectation expectIntent(String intentId, Skill target) {
        ConversationExpectation expectation = new ConversationExpectation(target)
                .addIntent(intentId);
        expect(expectation);
        return expectation;
    }

    @Override
    public ConversationExpectation expectEntity(String entityType, Skill target) {
        ConversationExpectation expectation = new ConversationExpectation(target)
                .addEntity(entityType);
        expect(expectation);
        return expectation;
    }

    @Override
    public ConversationExpectation expect(ConversationExpectation expectation) {
        expectations.push(expectation);
        return expectation;
    }

    public DefaultConversationSessionImpl setParticipant(Person participant) {
        this.participant = participant;
        return this;
    }

    public ConversationParticipant getResponseTarget() {
        return responseTarget;
    }

    public DefaultConversationSessionImpl setResponseTarget(ConversationParticipant responseTarget) {
        this.responseTarget = responseTarget;
        return this;
    }
}
