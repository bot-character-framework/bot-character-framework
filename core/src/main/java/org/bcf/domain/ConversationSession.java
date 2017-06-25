package org.bcf.domain;

import org.bcf.Skill;

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
    void clearExpectations();

    boolean persisted();

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
