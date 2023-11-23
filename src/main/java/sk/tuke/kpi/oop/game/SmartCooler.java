package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    public SmartCooler(Reactor reactor) {
        super(reactor);
    }

    private void smartCooler() {
        Reactor reactor;
        reactor = getConditionReactor();
        if(reactor == null){
            return;
        }
        if (getConditionReactor().getTemperature() <= 1500 && reactor != null) {
            turnOff();
        }
        else if (getConditionReactor().getTemperature() >= 2500 && reactor != null ){
            turnOn();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::smartCooler)).scheduleFor(this);
    }

}
