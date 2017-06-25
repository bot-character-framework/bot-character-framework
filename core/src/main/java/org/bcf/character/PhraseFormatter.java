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
