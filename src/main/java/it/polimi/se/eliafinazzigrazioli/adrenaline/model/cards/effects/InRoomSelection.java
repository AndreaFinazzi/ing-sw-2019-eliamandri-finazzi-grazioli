package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class InRoomSelection extends EffectState {

    final private WeaponEffect referenceSource;
    final private int sourceSelectionOrder;
    final private SelectableType selectionType;

    public InRoomSelection(WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType) {
        this.referenceSource = referenceSource;
        this.sourceSelectionOrder = sourceSelectionOrder;
        this.selectionType = selectionType;
    }

    @Override
    public void execute(WeaponCard invoker) {
        List<Selectable> toSelect = new ArrayList<>();
        Selectable reference;
        if (referenceSource != null)
            reference = referenceSource.getSelected(sourceSelectionOrder);
        else
            reference = invoker.getMatch().getCurrentPlayer();
        toSelect.addAll(reference.getByRoom(selectionType));
        //TODO event generation
    }
}
