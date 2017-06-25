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
package org.bcf.domain.base;

import org.bcf.domain.ConversationParticipant;

/**
 * Base abstract class implementing conversation participant
 *
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class BaseConversationParticipant implements ConversationParticipant {
    private String id;
    private String name;
    private String mentionName;

    public BaseConversationParticipant() {
    }

    public BaseConversationParticipant(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public BaseConversationParticipant setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public BaseConversationParticipant setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getMentionName() {
        return mentionName;
    }

    public BaseConversationParticipant setMentionName(String mentionName) {
        this.mentionName = mentionName;
        return this;
    }

}
