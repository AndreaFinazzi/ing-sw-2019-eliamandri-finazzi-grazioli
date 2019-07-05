package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.InputTestClass;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {
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
            connectionManager.disconnect();
            LOGGER.severe("EVENTS QUEUE INTERRUPTED FOREVER!!!");
        }
    });

    private boolean cli = false;
    private boolean rmi = false;
    private boolean test = false;
    private int networkPort;

    private int clientID;

    private String playerName;

    private RemoteView view;
    // Threads pool

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public Client(String args[]) {
        if (args.length > 0 && args[0] == "test")
            test = true;

    }

    public Client(boolean cli, boolean rmi) {
        this.cli = cli;
        this.rmi = rmi;
    }

    public Client(boolean cli, boolean rmi, int networkPort) {
        this(cli, rmi);
        this.networkPort = networkPort;
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

    public BlockingQueue<AbstractModelEvent> getEventsQueue() {
        return eventsQueue;
    }

    public void setDisconnected() {
        view.setDisconnected();
    }

    @Override
    public void run() {
        if (test) {
            setView(new InputTestClass(this));
            setConnectionManager(new ConnectionManagerSocket(this));
        } else {
            if (cli) {
                setView(new CLI(this));
            } else {
                GUI guiView = GUI.getInstance();
                guiView.setClient(this);
                setView(guiView);
            }

            if (rmi) {
                try {
                    setConnectionManager(new ConnectionManagerRMI(this, networkPort));
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                    disconnect();
                } catch (NotBoundException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            } else {
                setConnectionManager(new ConnectionManagerSocket(this, networkPort));
            }
        }
        init();
    }
}