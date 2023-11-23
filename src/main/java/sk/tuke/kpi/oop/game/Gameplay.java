package sk.tuke.kpi.oop.game;

/*public class Gameplay extends Scenario {

    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        Cooler cooler = new Cooler(reactor);
        Hammer hammer = new Hammer(1);

        scene.addActor(reactor, 64, 64);
        scene.addActor(cooler, 32, 32);
        scene.addActor(hammer, 200, 200);
        reactor.turnOn();

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(reactor::repair)
        ).scheduleFor(reactor);
    }
}*/
