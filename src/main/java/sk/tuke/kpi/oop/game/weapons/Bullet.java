package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.util.Objects;


public class Bullet extends AbstractActor implements Fireable {
    public Bullet() {
        Animation bulAnim = new Animation("sprites/bullet.png", 16, 16);
        setAnimation(bulAnim);
    }
    @Override
    public int getSpeed() {
        return 5;
    }
    @Override
    public void collidedWithWall() {
        Objects.requireNonNull(getScene()).removeActor(this);
    }

    @Override
    public void startedMoving(Direction direction) {
        if (direction != Direction.NONE && direction != null)
        {
            getAnimation().setRotation(direction.getAngle());
        }
    }
    public void pushIt() {
        if (getScene() == null)
        {
            return;
        }
        for (Actor actor : getScene().getActors())
        {
            if (actor instanceof Alive && !(actor instanceof Ripley && intersects(actor)))
            {
                ((Alive) actor).getHealth().drain(10);
                collidedWithWall();
                break;
            }
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::pushIt)).scheduleFor(this);
    }
}
