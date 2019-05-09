package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;

import java.util.Scanner;
import java.util.logging.Logger;

public class CLI {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName ());


    public static void main(String[] args) {
        Scanner input = new Scanner (System.in);
        System.out.println ("insert Player Name");
        String playername;
        playername = input.nextLine ();
        ConnectionManager connectionManager = new ConnectionManagerSocket (playername);
        AbstractEvent event = connectionManager.receive ();
        System.out.println ("message from server socket: " + event.getMessage ());
        System.out.println ("Insert message");
        String message = input.nextLine ();

        connectionManager.send (new GenericViewEvent (playername, message));
        event = connectionManager.receive ();
        System.out.println (event.getMessage ());
    }
}
