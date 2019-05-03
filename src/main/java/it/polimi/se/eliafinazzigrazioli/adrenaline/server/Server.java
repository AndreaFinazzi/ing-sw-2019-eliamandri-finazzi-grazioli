package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public void main(String[] args) {

        LOGGER.info(Messages.get("app.server.log.info.server_started", "DEFAULT: Server started!"));
    }
}
