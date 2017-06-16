package org.bcf.examplebot.skills;

import org.bcf.Bot;
import org.bcf.Skill;
import org.bcf.domain.StructuredMessage;
import org.bcf.domain.TextMessage;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class Hello implements Skill {
    @Override
    public boolean canHandle(String intentId) {
        return "greet".equals(intentId);
    }

    @Override
    public void handle(StructuredMessage message, Bot bot) {
        bot.reply(new TextMessage("Hi!").setRecipient(message.getOriginalMessage().getRoom()));
    }
}
