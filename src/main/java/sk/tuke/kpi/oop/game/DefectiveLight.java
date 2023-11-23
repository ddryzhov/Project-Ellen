package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{
    private Disposable disposingLight;
    private boolean repairedDefective;
    public DefectiveLight(){
        super();
        this.repairedDefective = false;
    }

    public void brokeLight() {
        this.repairedDefective = false;
        int i = (int) (Math.random() * 20);
        if (i == 1) {
            super.toggle();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.disposingLight = new Loop<>(new Invoke<>(this::brokeLight)
        ).scheduleFor(this);
    }


    @Override
    public boolean repair() {
        if (!repairedDefective && disposingLight != null) {
            repairedDefective = true;
            disposingLight.dispose();
            this.disposingLight = new ActionSequence<>(new Wait<>(10), new Loop<>(new Invoke<>(this::toggle))
            ).scheduleFor(this);
            return true;
        }
            return false;
    }
}
