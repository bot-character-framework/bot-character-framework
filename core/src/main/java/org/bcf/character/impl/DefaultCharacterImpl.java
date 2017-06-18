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

import org.bcf.character.Character;
import org.bcf.character.CharacterModel;
import org.bcf.exception.LineNotFound;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class DefaultCharacterImpl<P extends Enum<P>> implements Character<P> {

    private CharacterModel model;
    private Random random = new Random(LocalTime.now().toSecondOfDay());
    ;

    public DefaultCharacterImpl(CharacterModel model) {
        this.model = model;
    }

    public DefaultCharacterImpl() {
    }

    public CharacterModel getModel() {
        return model;
    }

    public DefaultCharacterImpl<P> setModel(CharacterModel model) {
        this.model = model;
        return this;
    }

    @Override
    public CharacterModel.Line getLine(String lineId, float formal, float emotional) throws LineNotFound {
        if (!model.getModel().containsKey(lineId)) {
            throw new LineNotFound(lineId);
        }
        List<CharacterModel.Line> variations = model.getModel().get(lineId).getVariations();
        int index = random.nextInt(variations.size() - 1);
        return variations.get(index);
    }

}
