package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events;

import java.io.Serializable;

public abstract class AbstractEvent implements Serializable {
    private String message;

    public AbstractEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
