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
package org.bcf;

import org.bcf.character.Character;
import org.bcf.domain.ConversationSession;
import org.bcf.domain.Message;
import org.bcf.domain.StructuredMessage;

import java.util.List;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface Bot<P extends Enum<P>> {
    void incomingMessage(Message message);

    void reply(Message message);

    List<Skill<? extends Bot<P>, P>> getSkillset();

    BotTransport getTransport();

    NLUModule getNLUModule();

    Character<P> getCharacter();

    ConversationSessionStorage<P> getSessionStorage();

    void onUnrecognizedIntent(StructuredMessage message);

    void onProcessingError(StructuredMessage message, Exception e);

    void initialize();

    ConversationSession<P> buildSessionForMessage(Message message);
}
