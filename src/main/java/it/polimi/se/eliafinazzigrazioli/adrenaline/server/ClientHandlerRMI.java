package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.util.logging.Logger;


/**
 * Client handler implementation for RMI connection. It handles outgoing events calling a specific method on the client's remote reference object.
 * <p>
 * Incoming objects are managed by ServerRMIManger, which exposes the remote interface.
 */

public class ClientHandlerRMI extends AbstractClientHandler {

    /**
     * Static reference to LOGGER.
     */
    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());

    /**
     * Reference to the client remote object
     */
    private ClientRemoteRMI clientRemoteRMI;


    /**
     * Instantiates a new Client handler rmi.
     *
     * @param serverRMIManager the server rmi manager with reference to server instance
     * @param clientRemoteRMI the client remote object reference
     * @param clientID the client id
     * @throws RemoteException the remote exception
     */
    public ClientHandlerRMI(ServerRMIManager serverRMIManager, ClientRemoteRMI clientRemoteRMI, int clientID) throws RemoteException {
        //TODO
        //listener = new RemoteView("tony");
        super(serverRMIManager.getServer());

        this.clientRemoteRMI = clientRemoteRMI;
        this.clientID = clientID;
    }

    /**
     * Calls a boolean method on remote object instance checking if it's reachable.
     *
     * @return the boolean
     * @throws RemoteException the remote exception
     */
    public boolean isReachable() throws RemoteException {
        return clientRemoteRMI.isReachable();
    }

    /**
     * Send method implementation. Calls the receive method on the remote object passing the event as a parameter.
     * Unregisters the corresponding client from match in case of error.
     *
     * @param event the event
     */
    @Override
    public void send(AbstractModelEvent event) {
        try {
            clientRemoteRMI.receive(event);
        } catch (RemoteException e) {
            LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + clientID);
            unregister();
        }
    }
}