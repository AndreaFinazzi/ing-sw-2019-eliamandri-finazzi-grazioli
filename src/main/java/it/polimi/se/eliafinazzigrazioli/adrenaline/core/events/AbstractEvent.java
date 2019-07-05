package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events;

import java.io.Serializable;

/**
 * The type Abstract event.
 */
public abstract class AbstractEvent implements Serializable {
    private String message;

    /**
     * Instantiates a new Abstract event.
     *
     * @param message the message
     */
    public AbstractEvent(String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
