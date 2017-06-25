package org.bcf.impl;

import org.bcf.Bot;
import org.bcf.Skill;
import org.bcf.domain.ConversationExpectation;
import org.bcf.domain.ConversationSession;
import org.bcf.domain.StructuredMessage;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public abstract class BaseSkill<B extends Bot<P>, P extends Enum<P>> implements Skill<B,P> {

    @Override
    public void handleExpectation(ConversationExpectation expectation, StructuredMessage message, ConversationSession<P> session) {

    }
}
