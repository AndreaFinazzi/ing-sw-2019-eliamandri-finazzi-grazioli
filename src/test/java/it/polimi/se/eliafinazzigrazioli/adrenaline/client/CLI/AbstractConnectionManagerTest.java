package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.AbstractConnectionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

public class AbstractConnectionManagerTest extends AbstractConnectionManager {

    public AbstractConnectionManagerTest(Client client) {
        super(client);
    }

    @Override
    public void send(AbstractViewEvent event) {
        System.out.println("Client ha spedito");
    }

    @Override
    public void init() {

    }

    @Override
    public void performRegistration() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void received(AbstractModelEvent event) {
        super.received(event);
    }

    @Override
    public String getPlayerName() {
        return super.getPlayerName();
    }

    @Override
    public void update(AbstractViewEvent event) {
        super.update(event);
    }

    @Override
    public void update(AbstractModelEvent event) {
        super.update(event);
    }
}
