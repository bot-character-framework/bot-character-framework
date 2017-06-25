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

import org.bcf.Bot;
import org.bcf.ConversationSessionStorage;
import org.bcf.domain.ConversationSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class InMemoryConversationSessionStorage<P extends Enum<P>> implements ConversationSessionStorage<P> {
    private Map<String, ConversationSession<P>> sessions = new HashMap<>();
    private Bot<P> bot;

    public InMemoryConversationSessionStorage(Bot<P> bot) {
        this.bot = bot;
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public ConversationSession<P> getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public boolean sessionExists(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @Override
    public void persistSession(ConversationSession<P> session) {
        if (session.getId() == null) throw new IllegalArgumentException("Session ID shouldn't be null");
        sessions.put(session.getId(), session);
    }

    @Override
    public void lock(String sessionId) {
        // This implementation doesn't support locks
    }

    @Override
    public void unlock(String sessionId) {
        // This implementation doesn't support locks
    }
}
