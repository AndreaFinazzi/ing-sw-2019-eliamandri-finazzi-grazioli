package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.Transitions;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;
import java.util.function.Function;

public class TransitionManager {

    public static final double TRANSITION_PAYMENT_DELTA_Y = -70;
    public static final double TRANSITION_PAYMENT_DURATION = 2;
    public static final double TRANSITION_PAYMENT_SCALING_FACTOR = 1.1;
    public static final double TRANSITION_PAYMENT_FADE_INITIAL = 1;
    public static final double TRANSITION_PAYMENT_FADE_FINAL = 0;

    public static Function<Node, Transition> powerUpPaymentAnimator = node -> {
        Duration duration = Duration.seconds(TransitionManager.TRANSITION_PAYMENT_DURATION);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(TransitionManager.TRANSITION_PAYMENT_DURATION + 1), node);

        fadeTransition.setFromValue(TransitionManager.TRANSITION_PAYMENT_FADE_INITIAL);
        fadeTransition.setToValue(TransitionManager.TRANSITION_PAYMENT_FADE_FINAL);

        return new ParallelTransition(fadeTransition);
    };

    public static Function<Node, Transition> ammoPaymentAnimator = node -> {
        Duration duration = Duration.seconds(TransitionManager.TRANSITION_PAYMENT_DURATION);

        TranslateTransition translateTransition = new TranslateTransition(duration, node);
        ScaleTransition scaleTransition = new ScaleTransition(duration, node);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(TransitionManager.TRANSITION_PAYMENT_DURATION + 1), node);

        translateTransition.setByY(TransitionManager.TRANSITION_PAYMENT_DELTA_Y);

        scaleTransition.setByX(TransitionManager.TRANSITION_PAYMENT_SCALING_FACTOR);
        scaleTransition.setByY(TransitionManager.TRANSITION_PAYMENT_SCALING_FACTOR);

        fadeTransition.setFromValue(TransitionManager.TRANSITION_PAYMENT_FADE_INITIAL);
        fadeTransition.setToValue(TransitionManager.TRANSITION_PAYMENT_FADE_FINAL);

        return new ParallelTransition(translateTransition, scaleTransition, fadeTransition);
    };


    public static ParallelTransition generateParallelTransition(Function<Node, Transition> animator, List<Node> payedElements) {
        ParallelTransition paymentTransition = new ParallelTransition();

        payedElements.forEach(element -> paymentTransition.getChildren().add(animator.apply(element)));

        return paymentTransition;
    }
}
