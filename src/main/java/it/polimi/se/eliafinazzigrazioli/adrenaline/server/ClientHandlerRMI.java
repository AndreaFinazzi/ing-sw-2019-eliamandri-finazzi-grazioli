package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.util.logging.Logger;


// RMI SERVER
public class ClientHandlerRMI extends AbstractClientHandler {

    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());

    private boolean set;
    private ServerRMIManager serverRMIManager;
    private ClientRemoteRMI clientRemoteRMI;


    public ClientHandlerRMI(ServerRMIManager serverRMIManager, ClientRemoteRMI clientRemoteRMI, int clientID) throws RemoteException {
        //TODO
        //listener = new RemoteView("tony");
        super(serverRMIManager.getServer());

        this.serverRMIManager = serverRMIManager;
        this.clientRemoteRMI = clientRemoteRMI;
        this.clientID = clientID;
    }

    public boolean isReachable() throws RemoteException {
        return clientRemoteRMI.isReachable();
    }

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