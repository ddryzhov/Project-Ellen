package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.weapons.Bullet;

public class EscapeRoom implements SceneListener {
    public static class Factory implements ActorFactory {

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            assert name != null;
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "alien mother":
                    return new AlienMother(200, new RandomlyMoving());
                case "ammo":
                    return new Ammo();
                case "door":
                    return new LockedDoor();
                case "alien":
                    return new Alien(100, new RandomlyMoving());
                default:
                    return null;
            }
        }
    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        scene.follow(ellen);

        Disposable movableCon = scene.getInput().registerListener(new MovableController(ellen));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ellen));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ellen));

        LockedDoor lockedDoor= new LockedDoor();
        scene.addActor(lockedDoor, 300, 100);

        AccessCard accessCard = new AccessCard();
        ellen.getBackpack().add(accessCard);
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ellen.decreaseEnergy());

        Bullet bullet = new Bullet();
        scene.addActor(bullet, 250, 100);

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Bullet) -> bullet.pushIt());

    }
    @Override
    public void sceneCreated(@NotNull Scene scene) {}

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Ripley ellen = scene.getFirstActorByType(Ripley.class);
        assert ellen != null;
        ellen.showRipleyState();
    }
}

