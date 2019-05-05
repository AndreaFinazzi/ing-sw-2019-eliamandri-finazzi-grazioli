package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.SelectableType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.ArrayList;
import java.util.List;

public class DistanceSelection extends EffectState {

    final private int minDistance;
    final private int maxDistance;
    final private WeaponEffect referenceSource;
    final private int sourceSelectionOrder;
    final private SelectableType selectionType;

    public DistanceSelection(int minDistance, int maxDistance, WeaponEffect referenceSource, int sourceSelectionOrder, SelectableType selectionType) {
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
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
        toSelect.addAll(reference.getByDistance(selectionType, minDistance, maxDistance));

    }
}
