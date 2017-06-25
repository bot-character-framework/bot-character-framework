package org.bcf.domain;

import org.bcf.Bot;
import org.bcf.Skill;

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
    public ConversationExpectation popExpectation() {
        if (expectations.isEmpty()) {
            return null;
        }
        return expectations.pop();
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
