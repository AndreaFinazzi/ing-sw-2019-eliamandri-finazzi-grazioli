package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManagerSocket extends ConnectionManger {

    private static final String IP_SERVER = "localhost";
    private static final int PORT_SEVER = 5000;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerSocket.class.getName ());
    private Socket clientSocket;
    private ObjectOutputStream send;
    private ObjectInputStream receveid;

    public ConnectionManagerSocket(String playername) {
        super(playername);
        try {
            clientSocket = new Socket (IP_SERVER, PORT_SEVER);
            send = new ObjectOutputStream (clientSocket.getOutputStream ());
            receveid = new ObjectInputStream (clientSocket.getInputStream ());
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public AbstractEvent receive(){
        AbstractEvent event; Object object;
        try {
            object = receveid.readObject ();
            if(object == null)
                throw new NullPointerException ();
            event = (AbstractEvent) object;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
            throw new NullPointerException ();
        }
        return event;
    }

    @Override
    public void send(AbstractViewEvent event) {
        try {
            send.writeObject (event);
            send.flush ();
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }
}
