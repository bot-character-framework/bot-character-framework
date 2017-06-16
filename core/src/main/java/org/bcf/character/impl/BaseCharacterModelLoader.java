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
import org.bcf.character.CharacterModelLoader;
import org.bcf.exception.ReadModelException;

import java.io.InputStream;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class BaseCharacterModelLoader implements CharacterModelLoader {

    @Override
    public CharacterModel loadModel(InputStream stream) throws ReadModelException {
        return loadModel(stream, CharacterModel.class);
    }

    protected void validateModel(CharacterModel model) throws ReadModelException {
        // Do not validate against default empty phrasebook
        if (model.getPhrasebook() == null) {
            return;
        }
        // Otherwise check if all of the phrases from phrasebook are in the model
        Enum[] phrases = model.getPhrasebook().getEnumConstants();
        for (Enum phrase : phrases) {
            if (!model.getModel().containsKey(phrase.name())) {
                throw new ReadModelException(
                        String.format("Model doesn't contain line for phrase %s which is listed in phrasebook."));
            }
        }
    }
}
