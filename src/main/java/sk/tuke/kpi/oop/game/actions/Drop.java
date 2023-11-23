package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {
            setDone(true);
            return;
        }
        if (getActor().getScene() == null  || getActor().getBackpack().peek() == null) {
            setDone(true);
            return;
        }
        if(isDone() == false) {
            Collectible lastSett = getActor().getBackpack().peek();
            assert lastSett != null;
            if (lastSett != null) {
                getActor().getBackpack().remove(lastSett);
                getActor().getScene().addActor(lastSett, getActor().getPosX(), getActor().getPosY());
            }
        }
        setDone(true);
    }
}

