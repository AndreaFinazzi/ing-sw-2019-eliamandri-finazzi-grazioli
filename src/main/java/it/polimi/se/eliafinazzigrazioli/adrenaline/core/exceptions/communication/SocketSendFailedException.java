package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.communication;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class SocketSendFailedException extends Exception {
    public SocketSendFailedException() {
        super(Messages.MESSAGE_EXCEPTIONS_NETWORK_SOCKET_SEND_FAILED);
    }
}
