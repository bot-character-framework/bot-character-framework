package org.bcf.character.impl;

import org.bcf.character.PhraseFormatter;

import java.util.Map;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class SimpleFormatter implements PhraseFormatter {
    @Override
    public String compilePhrase(String phrasePattern, Map<String, String> context) {
        String result = phrasePattern;
        for (Map.Entry<String, String> item : context.entrySet()) {
            result = result.replaceAll("${" + item.getKey() + "}", item.getValue());
        }
        return result;
    }
}
