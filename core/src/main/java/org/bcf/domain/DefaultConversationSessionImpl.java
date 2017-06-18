package org.bcf.domain;

import org.bcf.Bot;

import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class DefaultConversationSessionImpl<P extends Enum<P>> implements ConversationSession<P> {
    private String id;
    private List<Person> participants;
    private Person participant;
    private ConversationParticipant responseTarget;
    private transient Bot<P> bot;

    public DefaultConversationSessionImpl(Bot<P> bot) {
        this.bot = bot;
    }

    @Override
    public String getId() {
        return id;
    }

    public DefaultConversationSessionImpl setId(String id) {
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
    public void reply(P phrase) {
        TextMessage message = new TextMessage(bot.getCharacter().getLine(phrase).getText());
        message.setRecipient(getResponseTarget());
        bot.reply(message);
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
