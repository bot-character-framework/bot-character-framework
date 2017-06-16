/**
 * MIT License
 * <p>
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package org.bcf.character.impl;

import org.bcf.character.CharacterModel;
import org.bcf.exception.ReadModelException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
@RunWith(Parameterized.class)
public class YamlCharacterModelLoaderTest {
    private InputStream stream;
    private String filename;
    private YamlCharacterModelLoader modelLoader;

    @Parameterized.Parameters
    public static List<Object[]> modelsToLoad() {
        return Arrays.asList(new Object[][]{
                {"simple-valid-model.yml"},
                {"minimal-valid-model.yml"}
        });
    }

    public YamlCharacterModelLoaderTest(String fileName) throws Exception {
        this.stream = getClass().getResourceAsStream("/yaml-model/" + fileName);
        if (stream == null) {
            throw new Exception("File " + fileName + " is not found");
        }
        this.filename = fileName;
        this.modelLoader = new YamlCharacterModelLoader();
    }

    @After
    public void tearDown() throws Exception {
        if (stream != null) {
            stream.close();
        }
    }

    @Test
    public void shouldReadValidModelFilesWithNoErrors() throws ReadModelException {
        CharacterModel model = modelLoader.loadModel(stream);
    }
}