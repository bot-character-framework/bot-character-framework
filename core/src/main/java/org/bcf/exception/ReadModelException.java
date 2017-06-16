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
package org.bcf.exception;

/**
 * Represents exception related to character model read error
 * @author Dmitry Berezovsky (corvis)
 */
public class ReadModelException extends Exception {
    public ReadModelException(String message) {
        super(message);
    }

    public ReadModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadModelException(Throwable cause) {
        super(cause);
    }
}
