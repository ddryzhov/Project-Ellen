package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

public class RandomlyMoving implements Behaviour<Movable> {
    @Override
    public void setUp(Movable movable) {
        if (movable == null)
        {
            return;
        }
            new Loop<>(new ActionSequence<>(new Invoke<>(this::makeRandomMove), new Wait<>(2))).scheduleFor(movable);
    }

    public void makeRandomMove(Movable actor) {
        Direction smerAll = null;
        int mnoz = (int) (Math.random() * (3));
        int smerDefault = mnoz - 1;

        for (Direction integer : Direction.values())
        {
            if (smerDefault == integer.getDy() && smerDefault == integer.getDx())
            {
                    smerAll = integer;
            }
        }
        assert smerAll != null;
        actor.getAnimation().setRotation(smerAll.getAngle());
        new Move<>(smerAll, 2).scheduleFor(actor);
    }

}

