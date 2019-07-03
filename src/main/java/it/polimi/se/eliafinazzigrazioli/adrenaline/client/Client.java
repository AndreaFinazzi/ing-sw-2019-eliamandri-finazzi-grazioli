package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.InputTestClass;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private boolean active = true;
    private BlockingQueue<AbstractModelEvent> eventsQueue = new LinkedBlockingQueue<>();
    private AbstractConnectionManager connectionManager = null;

    private Thread eventsConsumer = new Thread(() -> {

        AbstractModelEvent nextEvent;
        try {
            while (isActive()) {
                nextEvent = eventsQueue.take();
                connectionManager.update(nextEvent);
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        } finally {

        }

        LOGGER.severe("EVENTS QUEUE INTERRUPTED FOREVER!!!");
    });

    private int clientID;

    private String playerName;

    private RemoteView view;
    // Threads pool

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public Client(String[] args) {

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RemoteView getView() {
        return view;
    }

    public void setView(RemoteView view) {
        this.view = view;
    }

    public void setConnectionManager(AbstractConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void init() {
        eventsConsumer.setName("Client::EventsConsumerThread");
        eventsConsumer.start();

        connectionManager.init();
    }

    private void disconnect() {
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String commandView, commandNetwork;
        Client client = new Client(args);

        if (args.length > 0 && args[0].equals("test")) {
            client.setView(new InputTestClass(client));
            client.setConnectionManager(new ConnectionManagerSocket(client));
            client.init();
        }


        System.out.println("to CLI or not to CLI? [Y/n]");
        commandView = input.nextLine();
        if (commandView.equals("n") || commandView.equals("no") || commandView.equals("not")) {
            GUI guiView = GUI.getInstance(args);
            guiView.setClient(client);
            client.setView(guiView);

        } else {
            client.setView(new CLI(client));
        }

        System.out.println("to RMI or not to RMI? [Y/n]");
        commandNetwork = input.nextLine();
        if (commandNetwork.equals("n") || commandNetwork.equals("no") || commandNetwork.equals("not")) {
            client.setConnectionManager(new ConnectionManagerSocket(client));
        } else {
            try {
                client.setConnectionManager(new ConnectionManagerRMI(client));
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                client.disconnect();
            } catch (NotBoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        client.init();
    }

    public BlockingQueue<AbstractModelEvent> getEventsQueue() {
        return eventsQueue;
    }

    public void setDisconnected() {
        view.setDisconnected();
    }
}