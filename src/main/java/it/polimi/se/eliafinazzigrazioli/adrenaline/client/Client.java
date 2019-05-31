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

    public void init() {
        connectionManager.listen();
        new Thread(view).start();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command;
        Client client = new Client(args);

        System.out.println("to CLI or not to CLI? [Y/n]");
        command = input.nextLine();
        if (command == "n" || command == "no" || command == "not") {
            client.setView(new GUI(args));
        } else {
            client.setView(new CLI());
        }

        System.out.println("to RMI or not to RMI? [Y/n]");
        command = input.nextLine();
        if (command == "n" || command == "no" || command == "not") {
            client.setConnectionManager(new ConnectionManagerSocket(client.getView()));
        } else {
            try {
                client.setConnectionManager(new ConnectionManagerRMI(client.getView()));
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            } catch (NotBoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

        client.connectionManager.listen();
        client.getView().login();
    }
}