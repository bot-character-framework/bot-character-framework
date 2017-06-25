package org.bcf.domain;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface ConversationContext {
    String getSessionId();

    Object get(String key);

    void put(String key, Object value);

}
