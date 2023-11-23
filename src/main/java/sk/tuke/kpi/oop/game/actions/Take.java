package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;
import java.util.List;
import java.util.Objects;


public class Take<A extends Keeper> extends AbstractAction<A> {
    public Take() {}

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {
            setDone(true);
            return;
        }
        if(getActor().getScene() == null){
            setDone(true);
            return;
        }
        Scene scene = getActor().getScene();
        if (!isDone()) {
            List<Actor> settingsArray;
            settingsArray = Objects.requireNonNull(getActor().getScene()).getActors();

            for (Actor settings : settingsArray) {
                if (settings instanceof Collectible && settings.intersects(getActor())) {
                    try {
                        getActor().getBackpack().add(((Collectible) settings));
                        assert scene != null;
                        scene.removeActor(settings);
                        break;
                    }
                    catch (IllegalStateException exception) {
                        int x = 0;
                        int y = 0;
                        int dur = 2;
                        scene.getOverlay().drawText(exception.getMessage(), x, y).showFor(dur);
                    }
                }
            }
            setDone(true);
        }
    }
}
