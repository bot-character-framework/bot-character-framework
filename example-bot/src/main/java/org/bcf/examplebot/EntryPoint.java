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
package org.bcf.examplebot;

import org.bcf.examplebot.nlu.RasaNLU;
import org.bcf.examplebot.transport.SlackTransport;
import org.bcf.exception.CharacterException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Dmitry Berezovsky (corvis)
 */
public class EntryPoint {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.append("You should bass 1 argument containing slack token\n");
            return;
        }
        String token = args[0];
        SlackTransport transport = new SlackTransport(token);
        transport.initialize();
        try {
            RasaNLU nluModule = new RasaNLU(new URL("http://localhost:5000/parse")).setModelName("weather");
            MyBot botimpl = new MyBot();
            botimpl.setNLUModule(nluModule);
            botimpl.setTransport(transport);
            botimpl.initialize();
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException | MalformedURLException | CharacterException e) {
            e.printStackTrace();
        } finally {
            transport.beforeDestroy();
        }
    }
}
