package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket  implements Runnable, ViewEventsListenerInterface {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;
    private Server server;

    private AbstractViewEvent event;
    private static final Logger LOGGER = Logger.getLogger (ClientHandlerSocket.class.getName ());


    public ClientHandlerSocket(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            receiver = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public void handleEvent(PlayerConnectedEvent event) throws HandlerNotImplementedException {
        System.out.println("funzione!!!!");
        try {
            sender.writeObject("funziona");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            try {
                    System.out.println("entrato");
                    event = (AbstractViewEvent) receiver.readObject();
                    event.handle(this);
                    Thread.sleep(1000);
            } catch (EOFException e) {
                System.out.println("Me ne esco");
                return;
            } catch(ClassCastException|IOException|ClassNotFoundException| HandlerNotImplementedException| InterruptedException e){
                e.printStackTrace();
            }
        } while (event != null);
    }
}