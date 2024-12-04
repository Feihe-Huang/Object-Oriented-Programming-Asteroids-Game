package si.model;

import java.awt.*;                         //日后要改成其他名字，用来放陨石和外星飞船
import java.util.Random;

public class EnemyShip implements Hittable {
    private boolean alive;
    public double x, y;
    public AlienType type;
    private Random rand;
    public static  final  int LAR_ASTEROIDS_SCALE = 10;
    public static final int MED_ASTEROIDS_SCALE = 4;
    public static final int SMA_ASTEROIDS_SCALE = 8;
    public static final int SHIP_SCALE = 3;
    double rX, rY;


    public EnemyShip(int x, int y, double rX, double rY, AlienType type) {
        this.x = x;
        this.y = y;
        this.rX = rX;
        this.rY = rY;
        this.type = type;
        this.rand = new Random(x * 100 + y);
        this.alive = true;
    }

    //判断发生了碰撞并且被子弹打到的物体死亡
    @Override
    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override
    public boolean isHitPlayer(Player player){
        Rectangle e = player.getHitBox();
        if (e.intersects(getHitBox().getBounds())){
            alive = false;
        }
        return  e.intersects(getHitBox().getBounds());
    }

    public boolean isHitAlienShips(AlienShips alienShips){
        Rectangle e = alienShips.getHitBox();
        if (e.intersects(getHitBox().getBounds())){
            alive = false;
        }
        return  e.intersects(getHitBox().getBounds());
    }

    //得到长方形hitbox
    @Override
    public Rectangle getHitBox() {         // 设置陨石的   hitbox    本来没有加和减的操作   +5  +6
        return new Rectangle((int) x + 5, (int) y + 6, SHIP_SCALE * type.getWidth(), SHIP_SCALE * type.getHeight());
    }

    @Override
    public AlienType getAlienType() {
        return type;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isEnemyShip() {
        return true;
    }

    public void move() {
        x += rX ;
        y += rY ;
    }

    public Shape getShape() {
        return new Rectangle();
    }

    final public int getX() {
        return (int) x;
    }

    final public int getY() {
        return (int) y;
    }


    public AlienType getType() {
        return type;
    }

}
