/**
 * MIT License
 * <p>
 * Bot Character Framework - Java framework for building smart bots
 * Copyright (c) 2017 Dmitry Berezovsky https://github.com/corvis/bot-character-framework
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */
package org.bcf.domain.base;

import org.bcf.domain.ConversationParticipant;
import org.bcf.domain.Message;
import org.bcf.domain.Person;
import org.bcf.domain.Room;

import java.time.LocalDate;

/**
 * Base class for representing chat messages
 * @author Dmitry Berezovsky (corvis)
 */
public class BaseMessage implements Message {
    private ConversationParticipant recipient;
    private Person sender;
    private Room room;
    private LocalDate dateSent;
    private LocalDate dateDelivered;
    private String text;

    @Override
    public ConversationParticipant getRecipient() {
        return recipient;
    }

    public BaseMessage setRecipient(ConversationParticipant recipient) {
        this.recipient = recipient;
        return this;
    }

    @Override
    public Person getSender() {
        return sender;
    }

    public BaseMessage setSender(Person sender) {
        this.sender = sender;
        return this;
    }

    @Override
    public LocalDate getDateSent() {
        return dateSent;
    }

    public BaseMessage setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
        return this;
    }

    @Override
    public LocalDate getDateDelivered() {
        return dateDelivered;
    }

    public BaseMessage setDateDelivered(LocalDate dateDelivered) {
        this.dateDelivered = dateDelivered;
        return this;
    }

    @Override
    public String getText() {
        return text;
    }

    public BaseMessage setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    public BaseMessage setRoom(Room room) {
        this.room = room;
        return this;
    }
}
