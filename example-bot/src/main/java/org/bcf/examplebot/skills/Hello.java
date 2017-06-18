package org.bcf.examplebot.skills;

import org.bcf.Bot;
import org.bcf.Skill;
import org.bcf.domain.ConversationParticipant;
import org.bcf.domain.ConversationSession;
import org.bcf.domain.StructuredMessage;
import org.bcf.domain.TextMessage;
import org.bcf.examplebot.MyBot;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class Hello implements Skill<MyBot, MyBot.PhraseBook> {
    @Override
    public boolean canHandle(String intentId) {
        return "greet".equals(intentId);
    }

    @Override
    public void handle(StructuredMessage message, ConversationSession<MyBot.PhraseBook> session) {
        session.reply(MyBot.PhraseBook.OK);
    }
}
