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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bcf.character.CharacterModel;
import org.bcf.exception.ReadModelException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Model loader which supports YAML representation of the model
 * @author Dmitry Berezovsky (corvis)
 */
public class YamlCharacterModelLoader extends BaseCharacterModelLoader {

    @Override
    public <T extends CharacterModel> T loadModel(InputStream stream, Class<T> modelClass) throws ReadModelException {
        YAMLFactory yamlFactory = new YAMLFactory();
        ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
        T result = null;
        try {
            result = objectMapper.readValue(stream, modelClass);
        } catch (IOException e) {
            throw new ReadModelException(e);
        }
        validateModel(result);
        return result;
    }
}
