package org.bcf.examplebot.skills;

import org.bcf.Bot;
import org.bcf.Skill;
import org.bcf.domain.ConversationSession;
import org.bcf.domain.StructuredMessage;
import org.bcf.examplebot.MyBot;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class WeatherInfo implements Skill<MyBot, MyBot.PhraseBook> {
    @Override
    public boolean canHandle(String intentId) {
        return "get_forecast".equals(intentId);
    }

    @Override
    public void handle(StructuredMessage message, ConversationSession session) {

    }

}
