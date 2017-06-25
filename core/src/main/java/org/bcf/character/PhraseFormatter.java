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
package org.bcf.character;

import java.util.Map;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface PhraseFormatter {
    String compilePhrase(String phrasePattern, Map<String, String> context);

    default CharacterModel.Line compilePhrase(CharacterModel.Line line, Map<String, String> context) {
        String compiled = compilePhrase(line.getText(), context);
        if (compiled.equals(line.getText())) {
            return line;
        } else {
            return new CharacterModel.Line()
                    .setEmotional(line.getEmotional())
                    .setFormal(line.getFormal())
                    .setText(compiled);
        }
    }
}
