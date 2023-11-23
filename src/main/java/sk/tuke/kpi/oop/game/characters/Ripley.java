package sk.tuke.kpi.oop.game.characters;


import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.Objects;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Animation playerAnimation;
    private Animation playerAnimationDie;
    private Backpack backpack;
    private int speed;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private Health health;
    private String playerDie = "sprites/player.png";
    private Firearm gun;
    private int ammo;
    private int energy;
    private Disposable disposable = null;

    public Ripley() {
        super("Ellen");
        playerAnimation = new Animation(playerDie, 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(playerAnimation);
        speed = 2;
        int full = 100;
        energy = 100;
        ammo = 100;
        backpack = new Backpack("Ripley's backpack",10);
        gun = new Gun(full,150);
        health = new Health(full, full);
        health.onExhaustion(() -> {
            this.setAnimation(new Animation(playerDie,32,32,0.1f, Animation.PlayMode.ONCE));
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED,this);
        });
    }

    @Override
    public int getSpeed() {
        return speed;
    }
    public Disposable cancelEnergy() {
        return disposable;
    }

    public int getEnergy() {
        return energy;
    }
    public int getAmmo() { return ammo; }


    @Override
    public Firearm getFirearm() {
        return gun;
    }

    public void decreaseEnergy() {
        if (health.getValue() > 0) {
            disposable = new Loop<>(new ActionSequence<>(new Invoke<>(() -> {
                if (this.health.getValue() <= 0)
                {
                    setAnimation(playerAnimationDie);
                    Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
                }
                else
                {
                    this.getHealth().drain(5);
                }
            }), new Wait<>(1))).scheduleFor(this);
        }
        else if (health.getValue() <= 0)
        {
            playerAnimationDie = new Animation(playerDie, 32, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(playerAnimationDie);
            Objects.requireNonNull(getScene()).getMessageBus().publish(RIPLEY_DIED, this);
        }
    }
    @Override
    public void setFirearm(Firearm weapon) {
        gun = weapon;
    }


    @Override
    public void startedMoving(Direction direction) {
        playerAnimation.setRotation(direction.getAngle());
        playerAnimation.play();
    }
    @Override
    public void stoppedMoving() {
        playerAnimation.stop();
    }


    @Override
    public Backpack getBackpack() {
        return backpack;
    }
    public void showRipleyState() {
        int windowHeight = Objects.requireNonNull(getScene()).getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        int xOne = 120;
        int xTwo = 280;
        getScene().getGame().getOverlay().drawText("Energy " + health.getValue(), xOne, yTextPos);
        getScene().getGame().getOverlay().drawText("Your Ammo: " + this.getAmmo(), xTwo, yTextPos);
    }


    @Override
    public Health getHealth() {
        return health;
    }
}
