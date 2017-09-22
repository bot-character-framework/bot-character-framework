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

import org.bcf.domain.base.BasePredictedEntity;

import java.io.Serializable;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class Entity extends BasePredictedEntity implements Serializable {
    private String typeId;
    private String value;
    private int startPosition;
    private int endPosition;
    private String additionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }


    public String getValue() {
        return value;
    }

    public Entity setValue(String value) {
        this.value = value;
        return this;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public Entity setStartPosition(int startPosition) {
        this.startPosition = startPosition;
        return this;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public Entity setEndPosition(int endPosition) {
        this.endPosition = endPosition;
        return this;
    }

    public String getTypeId() {
        return typeId;
    }

    public Entity setTypeId(String typeId) {
        this.typeId = typeId;
        return this;
    }
}
