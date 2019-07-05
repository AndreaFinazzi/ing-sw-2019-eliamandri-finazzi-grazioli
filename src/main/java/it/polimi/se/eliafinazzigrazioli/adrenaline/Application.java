package it.polimi.se.eliafinazzigrazioli.adrenaline;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static final String CONFIG_FILE_PATH = "/config/app.properties";

    private static final String APP_PARAMETERS_CLIENT = "client";
    private static final String APP_PARAMETERS_CLIENT_RMI = "rmi";
    private static final String APP_PARAMETERS_CLIENT_CLI = "cli";

    public static void main(String[] args) {
        List<String> arguments = new ArrayList<>(Arrays.asList(args));

        Runnable application;
        if (arguments.contains(APP_PARAMETERS_CLIENT)) {
            System.out.println("Type a free network port: ");
            int port = CLIUtils.nextInt(new Scanner(System.in), Integer.MAX_VALUE);
            boolean cli = arguments.contains(APP_PARAMETERS_CLIENT_CLI);
            boolean rmi = arguments.contains(APP_PARAMETERS_CLIENT_RMI);
            application = new Client(cli, rmi, port);
        } else {
            application = new Server();
        }

        application.run();
    }
}
