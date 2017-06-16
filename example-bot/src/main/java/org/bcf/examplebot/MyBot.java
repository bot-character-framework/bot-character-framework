package org.bcf.examplebot;

import org.bcf.CharacterFactory;
import org.bcf.Skill;
import org.bcf.character.CharacterModel;
import org.bcf.examplebot.skills.Hello;
import org.bcf.exception.CharacterException;
import org.bcf.impl.DefaultBotImpl;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class MyBot extends DefaultBotImpl {
    static List<Skill> SKILLS = Arrays.asList(new Hello());

    @Override
    public List<Skill> getSkillset() {
        return SKILLS;
    }

    public MyBot() throws CharacterException {
        setCharacter(new CharacterFactory().createCharacterFormModel(new CharacterModel()));
    }
}
