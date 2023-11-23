package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;

import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.items.Hammer;

public class TrainingGameplay extends Scenario {
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
}
