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
