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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents message processed by NLP core with identified intent and entities.
 * @author Dmitry Berezovsky (corvis)
 */
public class StructuredMessage implements Serializable {
    private String text;
    private Intent intent;
    private List<Intent> alternativeIntents = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private Message originalMessage;

    public String getText() {
        return text;
    }

    public StructuredMessage setText(String text) {
        this.text = text;
        return this;
    }

    public Intent getIntent() {
        return intent;
    }

    public StructuredMessage setIntent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public List<Intent> getAlternativeIntents() {
        return alternativeIntents;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Message getOriginalMessage() {
        return originalMessage;
    }

    public StructuredMessage setOriginalMessage(Message originalMessage) {
        this.originalMessage = originalMessage;
        return this;
    }
}
