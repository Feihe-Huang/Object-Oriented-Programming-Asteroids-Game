package si.model;

import java.awt.*;
import java.util.Random;

public class Bullet implements Movable, Hittable{
    private int x, y;
    private boolean aliendirection;  // True for up, false for down
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 5;   //设置子弹的大小
    public static final int BULLET_WIDTH = 3;
    private static final int BULLET_SPEED = 5;
    public int count = 50;
    // Player ship;
    int motionX, motionY;

    //考虑使用count 依次减少使子弹消失

    public Bullet(int x, int y, boolean alienDegree, String name, Player ship) {   //控制子弹出现位置，子弹方向，发射子弹的对象
        this.aliendirection = alienDegree;
        if (ship != null) {
            this.x = (int) (x + (52 * Math.sin(ship.degrees)));      //系数控制子弹发射点离船的距离
            this.y = (int) (y - ((50) * Math.cos(ship.degrees)));

            motionX = (int) (8 * Math.sin(ship.degrees));          //系数控制子弹速度，  可用于实现额外功能
            motionY = (int) ((-8) * Math.cos(ship.degrees));
        }else{
            this.x = x;
            this.y = y;
            Random r = new Random();
            boolean d = r.nextBoolean();
            if(d) {
                motionX = (int) (5 * Math.sin(Math.PI / 3));
                motionY = (int) (5 * Math.cos(Math.PI / 3));
            } else {
                motionX = (int) (5 * Math.sin( - Math.PI / 3));
                motionY = (int) (5 * Math.cos( - Math.PI / 3));
            }
        }
        this.name = name + " " + bulletCounter++;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);


    }

    public void move() {
        if (!aliendirection) {
            x += motionX;
            y  += motionY;
        } else {
            x += motionX;
            y += motionY;
        }
        //实现子弹从屏幕一端出现到另一端的操作
        x %= AsteroidsGame.SCREEN_WIDTH;   //从x，y到最大值的地方返回最小值的地方
        y %= AsteroidsGame.SCREEN_HEIGHT;

        x = x < 0 ? x + AsteroidsGame.SCREEN_WIDTH : x;
        y = y < 0 ? y + AsteroidsGame.SCREEN_HEIGHT : y;

        hitBox.setLocation(x, y);

        count--;
        if (count == 0)
            destroy();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    @Override
    public boolean isHitPlayer(Player player) {
        return false;
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    @Override
    public AlienType getAlienType() {
        return null;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isEnemyShip() {
        return false;
    }

    public void destroy() {
        alive = false;
    }
}
