package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

// Necessary to handle events queue
public class MatchBuilder implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatchBuilder.class.getName());

    private MatchController matchController;

    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public BlockingQueue<AbstractViewEvent> getEventsQueue() {
        return eventsQueue;
    }

    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
    }


    public MatchBuilder() {
        eventsQueue = new LinkedBlockingQueue<>();
        matchController = new MatchController();
        //this.clients = clients;  This line assigns clients to itself TODO correct assignment

        new Thread(() -> {

            AbstractViewEvent nextEvent;
            try {
                while (!matchController.getMatch().isEnded()) {
                    nextEvent = eventsQueue.take();
                    matchController.getEventController().update(nextEvent);
                }
            } catch (InterruptedException e) {
                //TODO handle
            } finally {

            }

        }).start();
    }

    public MatchController getMatchController() {
        return matchController;
    }

    public void startMatch() {
        // Add next match EventController as an Observer of RemoteViews
        // TODO bind match/queue/clientHandlers
        ArrayList<String> nextPlayingPlayers = matchController.getPlayersNicknames();
        for (String player : nextPlayingPlayers) {
            // playerToClientHandler.get(player).bindViewToEventController(nextMatch.getEventController());
        }
    }

    @Override
    public void run() {
        matchController.initMatch(MapType.ONE);
    }
}
