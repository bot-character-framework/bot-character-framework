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

import org.bcf.Skill;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface ConversationSession<P extends Enum<P>> extends Serializable {
    /**
     * Return session id
     * @return
     */
    String getId();

    /**
     * Returns identifier of room or user to post reply to.
     * @return
     */
    ConversationParticipant getResponseTarget();

    /**
     * Returns the list of participants engaged in conversation
     * @return
     */
    List<Person> getParticipants();

    /**
     * Returns the last participant posted something into this session
     * @return
     */
    Person getParticipant();

    ConversationExpectation popExpectation();

    @NotNull
    ConversationContext getContext();

    void clearExpectations();

    boolean persisted();

    void reply(Message message);

    void reply(P phrase, Map<String, String> context);

    default void reply(P phrase) {
        reply(phrase, Collections.emptyMap());
    }

    ConversationExpectation expectIntent(String intentId, Skill target);

    ConversationExpectation expectEntity(String entityType, Skill target);

    ConversationExpectation expect(ConversationExpectation expectation);

    default void expect(List<ConversationExpectation> expectations) {
        for (ConversationExpectation e : expectations) {
            expect(e);
        }
    }
    default void expect(ConversationExpectation... expectations) {
        for (ConversationExpectation e : expectations) {
            expect(e);
        }
    }
}
