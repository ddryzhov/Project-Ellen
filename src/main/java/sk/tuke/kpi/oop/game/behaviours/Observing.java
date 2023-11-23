package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.Objects;
import java.util.function.Predicate;

public class Observing <A extends Actor, T> implements Behaviour<A> {
    private A actor = null;
    private Predicate<T> predicate;
    private Topic<T> topic;
    private Behaviour<A> delegate;

    public Observing(Topic<T> topic, Predicate<T> predicate, Behaviour<A> delegate) {
        this.predicate = predicate;
        this.delegate = delegate;
        this.topic = topic;
    }

    @Override
    public void setUp(A actor) {
        this.actor = actor;
        if (actor != null)
        {
            Objects.requireNonNull(actor.getScene()).getMessageBus().subscribe(topic, this::testOne);
        }
    }

    private void testOne(T sprava) {
        if (actor == null)
        {
            return;
        }
        if(predicate.test(sprava) == false)
        {
            return;
        }
        delegate.setUp(actor);
    }

}
