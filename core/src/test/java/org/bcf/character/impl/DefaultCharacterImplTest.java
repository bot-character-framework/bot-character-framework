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

import org.bcf.character.Character;
import org.bcf.character.CharacterModel;
import org.bcf.exception.LineNotFound;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

/**
 * @author Dmitry Berezovsky (corvis)
 */
@RunWith(JUnit4.class)
public class DefaultCharacterImplTest {
    private static final String MODEL_FILE = "/yaml-model/weather-bot-model.yml";
    private Character character;
    private CharacterModel model;

    public DefaultCharacterImplTest() throws Exception {
        try (InputStream input = getClass().getResourceAsStream(MODEL_FILE)) {
            if (input == null) {
                throw new FileNotFoundException(MODEL_FILE);
            }
            YamlCharacterModelLoader yamlModelLoader = new YamlCharacterModelLoader();
            model = yamlModelLoader.loadModel(input);
        }
    }

    @Before
    public void setUp() throws Exception {
        character = new DefaultCharacterImpl(model);
    }

    @Test
    public void shouldReturnOneOptionWhenMultipleAvailable() throws LineNotFound {
        CharacterModel.Line line = character.getLine("OK");
        assertNotNull(line);
    }
}