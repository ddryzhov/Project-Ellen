package sk.tuke.kpi.oop.game;

public enum Direction {

    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NORTHWEST(-1, 1),
    NONE(0, 0);

    private final int dx;
    private float сorner;
    private final int dy;
    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    static {
        NORTH.сorner = 0;
        NORTHWEST.сorner = 45;
        WEST.сorner = 90;
        SOUTHWEST.сorner  = 135;
        SOUTH.сorner = 180;
        SOUTHEAST.сorner = 225;
        EAST.сorner = 270;
        NORTHEAST.сorner = 315;}
    public static Direction fromAngle(float angle) {
        if (angle == 0) {
            return NORTH;
        }
        if (angle == 45) {
            return NORTHWEST;
        }
        if (angle == 90) {
            return WEST;
        }
        if (angle == 135) {
            return SOUTHWEST;
        }
        if (angle == 180){
            return SOUTH;
        }
        if (angle == 225) {
            return SOUTHEAST;
        }
        if (angle == 270) {
            return EAST;
        }
        return NORTHEAST;
    }


    public float getAngle(){
        return сorner;
    }
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction combine(Direction otherSide) {
        for (Direction direct :Direction.values()) {
            int newDx = cout(dx + otherSide.dx);
            int newDy = cout(dy + otherSide.dy);

            if (direct.dx == newDx && direct.dy == newDy) {
                return direct;
            }
        }
        return NONE;
    }
    private static int cout(int value) {
        int min = -1;
        int max = 1;
        if(value < min){
            return min;
        }
        else{
            return Math.min(value, max);
        }
    }
}
