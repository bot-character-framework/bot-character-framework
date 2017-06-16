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
package org.bcf;

import org.bcf.character.Character;
import org.bcf.character.CharacterModel;
import org.bcf.character.CharacterModelLoader;
import org.bcf.character.impl.DefaultCharacterImpl;
import org.bcf.character.impl.YamlCharacterModelLoader;
import org.bcf.exception.CharacterException;
import org.bcf.exception.ReadModelException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class CharacterFactory {
    private static final YamlCharacterModelLoader YAML_LOADER = new YamlCharacterModelLoader();

    private <T extends Enum> Character<T> buildCharacter(Class<T> cls, CharacterModel model) throws CharacterException {
        try {
            return new DefaultCharacterImpl<T>().setModel(model);
        } catch (Exception e) {
            throw new CharacterException("Unable to build character. See inner exception for details.", e);
        }
    }

    private CharacterModel loadCharacterModel(CharacterModelLoader impl, InputStream stream) throws ReadModelException {
        return impl.loadModel(stream);
    }

    public Character createCharacterFormModel(CharacterModel model) throws CharacterException {
        return buildCharacter(model.getPhrasebook(), model);
    }

    public <T extends Enum> Character<T> createCharacterFormYamlModel(String filePath, Class<T> phrasebook) throws CharacterException, ReadModelException, IOException {
        try (FileInputStream input = new FileInputStream(filePath)) {
            CharacterModel model = loadCharacterModel(YAML_LOADER, input);
            return buildCharacter(phrasebook, model);
        }
    }

    public Character createCharacterFormYamlModel(InputStream inputStream, Class<Enum> phrasebook) throws CharacterException, ReadModelException {
        CharacterModel model = loadCharacterModel(YAML_LOADER, inputStream);
        return buildCharacter(phrasebook, model);
    }
}
