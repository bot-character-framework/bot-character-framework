package org.bcf.domain;

import org.bcf.Skill;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class ConversationExpectation {
    private String name;
    private List<String> expectedIntents = new ArrayList<>();
    private List<String> expectedEntities = new ArrayList<>();
    private String targetName;

    public ConversationExpectation(String targetName) {
        this.targetName = targetName;
    }

    public ConversationExpectation(Skill targetSkill) {
        this.targetName = targetSkill.getClass().getCanonicalName();
    }

    public ConversationExpectation(Class<? extends Skill> targetSkillClass) {
        this.targetName = targetSkillClass.getCanonicalName();
    }

    public ConversationExpectation addIntent(String intentId) {
        expectedIntents.add(intentId);
        return this;
    }

    public ConversationExpectation addEntity(String... entityType) {
        for (String e : entityType){
            expectedEntities.add(e);
        }
        return this;
    }

    public List<String> getExpectedIntents() {
        return expectedIntents;
    }

    public ConversationExpectation setExpectedIntents(List<String> expectedIntents) {
        this.expectedIntents = expectedIntents;
        return this;
    }

    public List<String> getExpectedEntities() {
        return expectedEntities;
    }

    public ConversationExpectation setExpectedEntities(List<String> expectedEntities) {
        this.expectedEntities = expectedEntities;
        return this;
    }

    public String getTargetName() {
        return targetName;
    }

    public ConversationExpectation setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public String getName() {
        return name;
    }

    public ConversationExpectation setName(String name) {
        this.name = name;
        return this;
    }
}
