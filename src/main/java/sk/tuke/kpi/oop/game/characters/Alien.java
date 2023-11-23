package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.Objects;


public class Alien extends AbstractActor implements Movable, Alive, Enemy {
    private Health health;
    private Disposable fight = null;
    private Behaviour<? super Alien> justDoing;
    private Animation alienAnim;
    private String alienAnimTwo = "sprites/alien.png";
    private int speed = 2;
    private int full = 100;

    public Alien() {
        alienAnim = new Animation(alienAnimTwo, 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnim);
        health = new Health(full, full);
        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
    }
    public Alien(int healthValue, Behaviour<? super Alien> behaviour) {
        alienAnim = new Animation(alienAnimTwo, 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(alienAnim);
        health = new Health(healthValue, full);
        justDoing = behaviour;
        health.onExhaustion(() -> Objects.requireNonNull(getScene()).removeActor(this));
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public Health getHealth() {
        return health;
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (justDoing != null)
        {
            justDoing.setUp(this);
        }
        fight = new Loop<>(new ActionSequence<>(new Invoke<>(this::runAlive), new Wait<>(0.3f))).scheduleFor(this);
    }

    public void runAlive() {
        if (getScene() == null) {
            return;
        }

        for (Actor playerAlive : getScene().getActors())
        {
            if ((playerAlive instanceof Enemy) == false && playerAlive instanceof Alive && this.intersects(playerAlive))
            {
                ((Alive) playerAlive).getHealth().drain(3);
                new ActionSequence<>(new Invoke<>(this::prepearingAlive), new Wait<>(1), new Invoke<>(this::runAlive)).scheduleFor(this);
            }
        }
    }
    public void prepearingAlive() {
        if (fight == null)
        {
            return;
        }
            fight = null;
    }

}
