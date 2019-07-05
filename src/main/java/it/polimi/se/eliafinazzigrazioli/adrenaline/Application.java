package it.polimi.se.eliafinazzigrazioli.adrenaline;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    private static final String APP_PARAMETERS_CLIENT = "client";
    private static final String APP_PARAMETERS_CLIENT_RMI = "rmi";
    private static final String APP_PARAMETERS_CLIENT_CLI = "cli";

    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));

        Runnable application;
        if (arguments.contains(APP_PARAMETERS_CLIENT)) {
            boolean cli = arguments.contains(APP_PARAMETERS_CLIENT_CLI);
            boolean rmi = arguments.contains(APP_PARAMETERS_CLIENT_RMI);
            application = new Client(cli, rmi);
        } else {
            application = new Server();
        }

        application.run();
    }
}
