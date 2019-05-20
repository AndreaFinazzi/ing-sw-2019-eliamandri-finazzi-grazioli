package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.WeaponCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface EventViewListenerRemote extends Remote {

    int getClientID() throws RemoteException;

    void setClientRMI(ClientRemoteRMI clientRMI) throws RemoteException;

    ClientRemoteRMI getClientRMI() throws RemoteException;

    boolean checkPlayerName() throws RemoteException;

    LightModel getLightModel() throws RemoteException;

    boolean response() throws RemoteException;

    String getResponseMessage() throws RemoteException;

    List<String> getPlayersConnected() throws RemoteException;

    List<String> getAvatarAvaible() throws RemoteException;

    default void handleEvent(AbstractViewEvent event) throws HandlerNotImplementedException, RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CardSelectedEvent event) throws HandlerNotImplementedException, RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CollectPlayEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(EffectSelectedEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(GenericViewEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(MovePlayEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayerConnectedEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayersSelectedEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PowerUpPlayEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(ReloadWeaponEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(RoomSelectedEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(SquareSelectedEvent event) throws HandlerNotImplementedException , RemoteException{
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(WeaponToUseSelectedEvent event) throws HandlerNotImplementedException, RemoteException{
        throw new HandlerNotImplementedException();
    }

}
