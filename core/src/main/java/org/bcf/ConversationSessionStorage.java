package org.bcf;

import org.bcf.domain.ConversationSession;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface ConversationSessionStorage<P extends Enum<P>> {
    ConversationSession<P> getSession(String sessionId);
    boolean sessionExists(String sessionId);
    void persistSession(ConversationSession<P> session);
    void lock(String sessionId);
    void unlock(String sessionId);

    default void lock(ConversationSession<P> session) {
        lock(session.getId());
    }
    default void unlock(ConversationSession<P> session) {
        unlock(session.getId());
    }

}
