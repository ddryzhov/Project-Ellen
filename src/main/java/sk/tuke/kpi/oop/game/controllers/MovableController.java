package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Move<Movable> move = null;

    private final Movable actor;
    private Disposable disposable;
    private final Set<Input.Key> key0;
    private Input.Key key1 = null;
    private Input.Key key2 = null;


    public MovableController(Movable actor) {
        this.actor = actor;
        key0 = new HashSet<>();
    }

    private final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (keyDirectionMap.containsKey(key))
        {
            key0.add(key);
            if(key1 == null)
            {
                key1 = key;
            }
            else if(key2 == null)
            {
                key2 = key;
            }
            updateMove();
        }
    }
    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if(keyDirectionMap.containsKey(key))
        {
            key0.remove(key);
            if (key == key1)
            {
                key1 = null;
            }
            if (key == key2)
            {
                key2 = null;
            }
            updateMove();
        }
    }
    private void updateMove() {
        Direction sideDir = null;
        int i = 0;
        for (Input.Key key100:key0)
        {
            if (i == 0)
            {
                sideDir = keyDirectionMap.get(key100);
            }
            if (i == 1)
            {
                sideDir = sideDir.combine(keyDirectionMap.get(key100));
            }
            i++;
        }
        stopMoving();
        if (sideDir != null)
        {
            move = new Move<>(sideDir, Float.MAX_VALUE);
            disposable = move.scheduleFor(actor);
        }
    }

    private void stopMoving() {
        if (move != null)
        {
            move.stop();
            disposable.dispose();
            move = null;
        }
    }
}
