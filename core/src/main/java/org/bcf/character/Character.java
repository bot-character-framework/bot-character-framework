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
package org.bcf.character;

import org.bcf.exception.LineNotFound;

import java.util.Collections;
import java.util.Map;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface Character<T extends Enum<T>> {

    CharacterModel.Line getLine(String lineId, Map<String, String> context, float formal, float emotional) throws LineNotFound;

    Character<T> setModel(CharacterModel model);

    default CharacterModel.Line getLine(String lineId, float formal, float emotional) throws LineNotFound {
        return getLine(lineId, Collections.emptyMap(), formal, emotional);
    }

    default CharacterModel.Line getLine(String lineId) throws LineNotFound {
        return getLine(lineId, 0, 0);
    }

    default CharacterModel.Line getLine(T phrase) {
        return getLine(phrase, Collections.emptyMap(), 0, 0);
    }

    default CharacterModel.Line getLine(T phrase, Map<String, String> context) {
        return getLine(phrase, context, 0, 0);
    }

    default String getLineText(String lineId, float formal, float emotional) throws LineNotFound {
        return getLine(lineId, formal, emotional).getText();
    }

    default CharacterModel.Line getLine(T phrase, Map<String, String> context, float formal, float emotional) {
        try {
            return getLine(phrase.name(), context, formal, emotional);
        } catch (LineNotFound lineNotFound) {
            throw new RuntimeException("Line not found in the model while model was pre-validated.", lineNotFound);
        }
    }
}
