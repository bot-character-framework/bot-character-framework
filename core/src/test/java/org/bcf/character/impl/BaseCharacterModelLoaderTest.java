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
package org.bcf.character.impl;

import org.bcf.character.CharacterModel;
import org.bcf.exception.ReadModelException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.InputStream;

/**
 * @author Dmitry Berezovsky (corvis)
 */
@RunWith(JUnit4.class)
public class BaseCharacterModelLoaderTest {

    public static class WeatherBotModel extends CharacterModel {
        public enum Phrase {
            OK, DO_NOT_UNDERSTAND, WEATHER_RESULT, IT_IS_HOT, IT_IS_COLD
        }

        public WeatherBotModel() {
            super(Phrase.class);
        }
    }

    private YamlCharacterModelLoader modelLoader = new YamlCharacterModelLoader();
    private InputStream stream;

    private InputStream getModelStream(String filePath) {
        stream = getClass().getResourceAsStream("/yaml-model/" + filePath);
        return stream;
    }

    @After
    public void tearDown() throws Exception {
        if (stream != null) {
            stream.close();
        }
    }

    @Test
    public void shouldReadValidModel() throws ReadModelException {
        CharacterModel model = modelLoader.loadModel(getModelStream("weather-bot-model.yml"));
    }
}