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
            RasaNLU nluModule = new RasaNLU(new URL("http://localhost:5000/parse"));
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
