package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {
    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch (name)
            {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "door":
                    return new LockedDoor();
                case "locker":
                    return new Locker();
                case "access card":
                    return new AccessCard();
                case "ventilator":
                    return new Ventilator();
                default: return null;
            }
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        scene.follow(ellen);

        Disposable movableController = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperController = scene.getInput().registerListener(new KeeperController(ellen));

        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        ellen.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ellen.getBackpack());

        AccessCard accessCard = new AccessCard();
        ellen.getBackpack().add(accessCard);

    }
    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}
