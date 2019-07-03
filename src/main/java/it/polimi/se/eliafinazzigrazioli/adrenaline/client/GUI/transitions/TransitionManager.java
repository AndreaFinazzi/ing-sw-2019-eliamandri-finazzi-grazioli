package it.polimi.se.eliafinazzigrazioli.adrenaline.client.GUI.transitions;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.List;
import java.util.function.Function;

public class TransitionManager {

    public static final double TRANSITION_PAYMENT_DELTA_Y = -70;
    public static final double TRANSITION_PAYMENT_DURATION = 2;
    public static final double TRANSITION_DEATH_DURATION = 1;
    public static final int TRANSITION_DEATH_CYCLE_COUNT = 5;
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

    public static Function<Node, Transition> deathTransition = node -> {
        Duration duration = Duration.seconds(TransitionManager.TRANSITION_DEATH_DURATION);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(TransitionManager.TRANSITION_DEATH_DURATION + 1), node);

        fadeTransition.setFromValue(TransitionManager.TRANSITION_PAYMENT_FADE_INITIAL);
        fadeTransition.setToValue(TransitionManager.TRANSITION_PAYMENT_FADE_FINAL);

        fadeTransition.setCycleCount(TRANSITION_DEATH_CYCLE_COUNT);

        return new ParallelTransition(fadeTransition);
    };

    public static ParallelTransition generateParallelTransition(Function<Node, Transition> animator, List<Node> elements) {
        ParallelTransition parallelTransition = new ParallelTransition();

        elements.forEach(element -> parallelTransition.getChildren().add(animator.apply(element)));

        return parallelTransition;
    }

    public static ParallelTransition generateSimpleTransition(Function<Node, Transition> animator, Node element) {
        ParallelTransition simpleTransition = new ParallelTransition();

        simpleTransition.getChildren().add(animator.apply(element));

        return simpleTransition;
    }
}
