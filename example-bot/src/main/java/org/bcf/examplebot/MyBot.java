package org.bcf.examplebot;

import org.bcf.Bot;
import org.bcf.CharacterFactory;
import org.bcf.Skill;
import org.bcf.examplebot.skills.Hello;
import org.bcf.exception.CharacterException;
import org.bcf.exception.ReadModelException;
import org.bcf.impl.DefaultBotImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class MyBot extends DefaultBotImpl<MyBot.PhraseBook> {
    public enum PhraseBook {
        OK
    }
    private static final String CHARACTER_MODEL_FILE = "/character-model.yml";
    private static List<Skill<? extends Bot<PhraseBook>, PhraseBook>> SKILLS = Arrays.asList(new Hello());

    @Override
    public List<Skill<? extends Bot<PhraseBook>, PhraseBook>> getSkillset() {
        return SKILLS;
    }

    public MyBot() throws CharacterException {
        try {
            setCharacter(new CharacterFactory().createCharacterFormYamlModel(getClass().getResourceAsStream(CHARACTER_MODEL_FILE), PhraseBook.class));
        } catch (ReadModelException e) {
            throw new CharacterException(e);
        }
    }
}
