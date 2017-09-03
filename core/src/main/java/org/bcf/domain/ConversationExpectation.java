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
package org.bcf.domain;

import org.bcf.Skill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class ConversationExpectation implements Serializable {
    private String name;
    private String targetName;
    private List<String> expectedIntents = new ArrayList<>();
    private List<String> expectedEntities = new ArrayList<>();
    private StructuredMessage source;

    public ConversationExpectation(String targetName) {
        this.targetName = targetName;
    }

    public ConversationExpectation(Skill targetSkill) {
        this.setTargetSkill(targetSkill);
    }

    public ConversationExpectation(Class<? extends Skill> targetSkillClass) {
        this.targetName = targetSkillClass.getCanonicalName();
    }

    public ConversationExpectation(ConversationExpectation original) {
        name = original.getName();
        targetName = original.getTargetName();
        expectedEntities.addAll(original.getExpectedEntities());
        expectedIntents.addAll(original.getExpectedIntents());
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

    public StructuredMessage getSource() {
        return source;
    }

    public ConversationExpectation setTargetSkill(Skill target) {
        targetName = target.getClass().getCanonicalName();
        return this;
    }

    public ConversationExpectation setSource(StructuredMessage source) {
        this.source = source;
        return this;
    }

    /**
     * Creates new expectation using current one as a template.
     * Given source will be set as a source for new expectation overriding existing value
     * @param source
     * @return new expectation
     */
    public ConversationExpectation newFromTemplate(StructuredMessage source) {
        return new ConversationExpectation(this).setSource(source);
    }

}
