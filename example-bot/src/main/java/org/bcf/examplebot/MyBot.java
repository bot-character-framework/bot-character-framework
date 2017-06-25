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
package org.bcf.examplebot;

import org.bcf.Bot;
import org.bcf.CharacterFactory;
import org.bcf.Skill;
import org.bcf.examplebot.skills.Hello;
import org.bcf.examplebot.skills.WeatherInfo;
import org.bcf.exception.CharacterException;
import org.bcf.exception.ReadModelException;
import org.bcf.impl.DefaultBotImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class MyBot extends DefaultBotImpl<MyBot.PhraseBook> {
    public enum PhraseBook {
        OK, ASK_FOR_LOCATION, TEMPERATURE, FORECAST
    }
    private static final String CHARACTER_MODEL_FILE = "/character-model.yml";
    private static List<Skill<? extends Bot<PhraseBook>, PhraseBook>> SKILLS = Arrays.asList(new Hello(), new WeatherInfo());

    @Override
    public List<Skill<? extends Bot<PhraseBook>, PhraseBook>> getSkillset() {
        return SKILLS;
    }

    public MyBot() throws CharacterException {
        try {
            setCharacter(new CharacterFactory().createCharacterFormYamlModel(getClass().getResourceAsStream(CHARACTER_MODEL_FILE), PhraseBook.class));
        } catch (ReadModelException e) {
            throw new CharacterException(e);
        }
    }
}
