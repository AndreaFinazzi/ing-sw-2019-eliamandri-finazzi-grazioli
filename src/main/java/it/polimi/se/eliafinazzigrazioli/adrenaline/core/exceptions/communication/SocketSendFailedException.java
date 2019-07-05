package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.communication;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Socket send failed exception.
 */
public class SocketSendFailedException extends Exception {
    /**
     * Instantiates a new Socket send failed exception.
     */
    public SocketSendFailedException() {
        super(Messages.MESSAGE_EXCEPTIONS_NETWORK_SOCKET_SEND_FAILED);
    }
}
