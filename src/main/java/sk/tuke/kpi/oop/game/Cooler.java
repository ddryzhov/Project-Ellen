package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private final Reactor reactor;
    private final Animation coolerOn;
    private boolean isOn;
    public Cooler(Reactor reactor){
        this.reactor = reactor;
        coolerOn = new Animation("sprites/fan.png", 32, 32, 0.2f);
        setAnimation(coolerOn);
        coolerOn.pause();
        isOn = false;
    }
    @Override
    public boolean isOn() {
        return isOn;
    }
    public Reactor getConditionReactor(){
        return reactor;
    }
    @Override
    public void turnOn() {
        isOn = true;
        coolerOn.play();
        //setAnimation(coolerOn);
    }
    @Override
    public void turnOff() {
        isOn = false;
        coolerOn.pause();
    }
    private void coolReactor() {
        if(reactor == null){
            return;
        }
        if (isOn) {
            this.reactor.decreaseTemperature(1);
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)
        ).scheduleFor(this);
    }

}
