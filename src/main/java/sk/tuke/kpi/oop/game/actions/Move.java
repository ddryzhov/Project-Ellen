package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

import java.util.Objects;


public class Move<A extends Movable> implements Action<A> {

    private A actor;
    private Direction direction;
    private int firstUse;
    private boolean isDone;
    private float duration;
    private int nullPoint = 0;


    public Move(Direction direction, float duration) {
        isDone = false;
        this.direction = direction;
        this.duration = duration;
        firstUse = 0;
    }

    private Move(Direction direction) {
        isDone = false;
        this.direction = direction;
        firstUse = 0;
    }

    @Nullable
    @Override
    public A getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable A movable) {
        this.actor = movable;
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    @Override
    public void reset() {
        isDone = false;
        actor.stoppedMoving();
        firstUse = 0;
        duration = 0;
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null)
        {
            return;
        }
        duration = duration - deltaTime;
        int posX =  (getActor().getPosX() + direction.getDx() * actor.getSpeed());
        int posY = (getActor().getPosY() + direction.getDy() * actor.getSpeed());

        int sidePosX = getActor().getPosX();
        int sidePosY = getActor().getPosY();
        if (isDone() == false)
        {
            if (firstUse == nullPoint )
            {
                actor.startedMoving(direction);
                firstUse++;
            }
            if (duration > nullPoint)
            {
                actor.setPosition(posX, posY);
                if ((Objects.requireNonNull(getActor().getScene())).getMap().intersectsWithWall(actor))
                {
                    actor.setPosition(sidePosX, sidePosY);
                    actor.collidedWithWall();
                }
            }
            else
            {
                stop();
            }
        }
    }

    public void stop() {
        if (actor != null)
        {
            isDone = true;
            actor.stoppedMoving();
        }
    }
}


