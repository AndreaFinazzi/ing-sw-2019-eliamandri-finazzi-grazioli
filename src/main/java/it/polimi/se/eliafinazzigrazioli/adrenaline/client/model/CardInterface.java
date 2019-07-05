package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

/**
 * The interface Card interface.
 */
public interface CardInterface {

    /**
     * Draw card string [ ] [ ].
     *
     * @return the string [ ] [ ]
     */
    public String[][] drawCard();

    /**
     * Draw card string [ ] [ ].
     *
     * @param light the light
     * @return the string [ ] [ ]
     */
    public String[][] drawCard(boolean light);
}
