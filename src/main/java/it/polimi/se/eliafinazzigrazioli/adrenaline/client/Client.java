package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.GUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());


    private int clientID;
    private String playerName;

    private AbstractConnectionManager connectionManager;
    private RemoteView view;

    // Threads pool
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    public Client(String[] args) {

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
        connectionManager.listen();
        new Thread(view).start();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;
        Client client = new Client(args);

        try {
            System.out.println("to CLI or not to CLI? [Y/n]");
            command = input.nextLine();
            if (command.equals("n") || command.equals("no") || command.equals("not")) {
                client.setView(new GUI(args, client));
            } else {
                client.setView(new CLI(client));
            }

            System.out.println("to RMI or not to RMI? [Y/n]");
            command = input.nextLine();
            if (command.equals("n") || command.equals("no") || command.equals("not")) {
                client.setConnectionManager(new ConnectionManagerSocket(client));
            } else {
                try {
                    client.setConnectionManager(new ConnectionManagerRMI(client));
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } catch (NotBoundException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }

            client.init();
        } finally {
            client.disconnect();
        }
    }

    private void disconnect() {
        if (connectionManager != null) {
            connectionManager.disconnect();
        }
    }

}