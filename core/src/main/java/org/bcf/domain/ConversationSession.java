package org.bcf.domain;

import java.io.Serializable;
import java.util.List;

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

    void reply(P phrase);
}
