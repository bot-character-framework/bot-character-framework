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

import org.bcf.domain.ConversationExpectation;
import org.bcf.domain.ConversationSession;
import org.bcf.domain.StructuredMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public interface Skill<B extends Bot<P>, P extends Enum<P>> {
    boolean canHandle(@NotNull String intentId);

    void handleMessage(@NotNull StructuredMessage message, @NotNull ConversationSession<P> session);

    /**
     * Handles given expectation. This method may return Structured message which will be processed next by bot.
     * If nothing returned bot should just wait for the next command.
     * @param expectation
     * @param message
     * @param session
     * @return
     */
    StructuredMessage handleExpectation(@NotNull ConversationExpectation expectation, @NotNull StructuredMessage message, @NotNull ConversationSession<P> session);
}
