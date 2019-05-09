package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.io.Serializable;
import java.rmi.Remote;


// RMI SERVER
public class ClientHandlerRMI extends AbstractClientHandler implements Remote, Serializable {

    private ConnectionManagerRMI clientRMI;
    private Server server;
    private AbstractEvent received;

    private AbstractEvent sended;

    public ClientHandlerRMI(Server server) {
        this.server = server;
    }

    public void setClientRMI(ConnectionManagerRMI clientRMI) {
        this.clientRMI = clientRMI;
    }

    @Override
    void send(AbstractEvent event) {
        clientRMI.setSended (event);
    }

    @Override
    public AbstractViewEvent receive() {
        return clientRMI.getReceived ();
    }

    public ConnectionManagerRMI getClientRMI() {
        return clientRMI;
    }

    public Server getServer() {
        return server;
    }

    public void setReceived(AbstractEvent received) {
        this.received = received;
    }

    public AbstractEvent getReceived() {
        return received;
    }

    public AbstractEvent getSended() {
        return sended;
    }

    public void setSended(AbstractEvent sended) {
        this.sended = sended;
    }
}
