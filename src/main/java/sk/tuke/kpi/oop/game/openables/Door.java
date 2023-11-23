package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Objects;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    private boolean isOpened;
    private  Animation doorOpenAnim;
    private enum  Orientation { VERTICAL, HORIZONTAL }
    private Animation doorCloseAnim;
    private String doorAnim = "sprites/vdoor.png";
    private String doorVertiAnim = "sprites/hdoor.png";

    public Door(){
        isOpened = false;
        doorOpenAnim = new Animation(doorAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        doorCloseAnim = new Animation(doorAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(new Animation(doorAnim, 16, 32));
        getAnimation().stop();
    }
    public Door (String name, Orientation orientation) {
        super(name);
        isOpened = false;
        if (orientation == Orientation.VERTICAL)
        {
            doorOpenAnim = new Animation(doorAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            doorCloseAnim = new Animation(doorAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(new Animation(doorAnim, 16, 32));
            getAnimation().stop();
        }
        else if (orientation == Orientation.HORIZONTAL)
        {
            doorOpenAnim = new Animation(doorVertiAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            doorCloseAnim = new Animation(doorVertiAnim, 16, 32, 0.1f, Animation.PlayMode.ONCE);
            setAnimation(new Animation(doorVertiAnim, 16, 32));
            getAnimation().stop();
        }
    }


        @Override
    public void useWith(Actor actor) {
        if (!isOpen())
        {
            open();
        }
        else{
            close();
        }
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        isOpened = true;
        int x = this.getPosX() / 16;
        int y = this.getPosY() / 16 + 1;
        Objects.requireNonNull(getScene()).getMessageBus().publish(DOOR_OPENED, this);
        Objects.requireNonNull(getScene()).getMap().getTile(x, getPosY() / 16).setType(MapTile.Type.CLEAR);
        getScene().getMap().getTile(x, y).setType(MapTile.Type.CLEAR);
        setAnimation(doorOpenAnim);
        getAnimation().play();
        getAnimation().stop();
    }

    @Override
    public void close() {
        isOpened = false;
        int x = this.getPosX() / 16;
        int y = this.getPosY() / 16 + 1;
        Objects.requireNonNull(getScene()).getMap().getTile(x, getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(x, y).setType(MapTile.Type.WALL);
        setAnimation(doorCloseAnim);
        getAnimation().play();
        getAnimation().stop();
        getScene().getMessageBus().publish(DOOR_CLOSED, this);
    }

    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        int x = this.getPosX() / 16;
        int y = this.getPosY() / 16 + 1;
        super.addedToScene(scene);
        Objects.requireNonNull(getScene()).getMap().getTile(x, this.getPosY() / 16).setType(MapTile.Type.WALL);
        getScene().getMap().getTile(x, y).setType(MapTile.Type.WALL);
    }
}
