package it.polimi.se.eliafinazzigrazioli.adrenaline;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.Server;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AdrenalineTest {


    @Test
    public void MatchConsistencyTest(){
/*
        Thread server = new Thread(() -> new Server().run());
        server.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Thread> clients = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            clients.add(new Thread(() -> {
                String[] args = new String[1];
                args[0] = "test";
                new Client(args).run();
            }));
        }
        for (Thread thread: clients) {
            thread.start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.interrupt();
        for (Thread thread: clients)
            thread.interrupt();



 */
    }
}
