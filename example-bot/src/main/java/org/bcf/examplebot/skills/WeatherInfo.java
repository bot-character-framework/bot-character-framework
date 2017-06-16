package org.bcf.examplebot.skills;

import org.bcf.Bot;
import org.bcf.Skill;
import org.bcf.domain.StructuredMessage;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class WeatherInfo implements Skill {
    @Override
    public boolean canHandle(String intentId) {
        return "get_forecast".equals(intentId);
    }

    @Override
    public void handle(StructuredMessage message, Bot bot) {
        StringBuilder sb = new StringBuilder();

    }
}
