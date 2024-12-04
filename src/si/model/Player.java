package si.model;

import si.display.PlayerListener;

import java.awt.*;

public class Player implements Hittable {
    public int x;
    public int y;
    private Rectangle hitBox;
    private int weaponCountdown;   // 子弹数量
    private boolean alive = true;
    public static final int SHIP_SCALE = 3;
    private static final int WIDTH = SHIP_SCALE * 8;
    public double degrees;            //旋转角度数
    double acceleration = 0.1;
    AlienType type;
    private double x1;
    private double y1;
    private int time = 150;
    PlayerListener listener;

    public Player(PlayerListener listener) {
        this.listener = listener;
        x = AsteroidsGame.SCREEN_WIDTH / 2;
        y = AsteroidsGame.SCREEN_HEIGHT / 2;
        hitBox = new Rectangle(x - AsteroidsGame.PLAYER_WIDTH * SHIP_SCALE + 2, y  - (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6) + AsteroidsGame.PLAYER_HEIGHT - 10) , 2 * AsteroidsGame.PLAYER_WIDTH * Player.SHIP_SCALE -2, (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6)) + AsteroidsGame.PLAYER_HEIGHT + 6);
//        hitBox = new Rectangle(-1, -1, 0, 0);  //hitbox 设置的无法触及
    }

    public boolean isHit(Bullet b) {
        Rectangle s = b.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());  //判断是否击中
    }

    //新添加的method   注意有没有出错
    @Override
    public boolean isHitPlayer(Player player){
        Rectangle e = player.getHitBox();
        if (e.intersects(getHitBox().getBounds())){
            alive = false;
        }
        return  e.intersects(getHitBox().getBounds());
    }

    @Override
    public Rectangle getHitBox() {
        if (time == 0 && !listener.hasPressedUnbeatable())
            return hitBox;
        return new Rectangle(-1000, -1000, 0, 0);
    }

    @Override
    public AlienType getAlienType() {
        return type;
    }

    //冷却时间
    public void tick() {
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 5;
        }
        if (time > 0)
            time--;
    }

    public int getTime(){
        return time;
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        alive = true;
        x = AsteroidsGame.SCREEN_WIDTH / 2;
        y = AsteroidsGame.SCREEN_HEIGHT / 2;

        time = 150;
    }

    public int getPoints() {
        return -100;
    }    //打到自己减100分

    public boolean isPlayer() {
        return true;
    }

    public boolean isEnemyShip() {
        return false;
    }


    public Bullet fire() {
        Bullet b = null;
        if (weaponCountdown == 0) {    //x  y
            b = new Bullet(x, y, true, "Player", this);  //x,y坐标控制子弹出现的位置
        }
        return b;
    }

    public void stop() {
        x1 = 0;
        y1 = 0;
    }

    public void moveUp(){
         x1 +=  ((acceleration) * Math.sin(degrees));
         y1 +=  ((-acceleration) * Math.cos(degrees));     //- 3   -10   +3  + 10    x1    y1
//        Rectangle newBox = new Rectangle(x - SpaceInvadersGame.PLAYER_WIDTH * SHIP_SCALE + 2, y  - (int) (SpaceInvadersGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6) + SpaceInvadersGame.PLAYER_HEIGHT - 10) , 2 * SpaceInvadersGame.PLAYER_WIDTH * Player.SHIP_SCALE -2, (int) (SpaceInvadersGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6)) + SpaceInvadersGame.PLAYER_HEIGHT + 6);
////        x += x1;
////        y += y1;
//        hitBox = newBox;

        x %= AsteroidsGame.SCREEN_WIDTH;   //从x，y到最大值的地方返回最小值的地方
        y %= AsteroidsGame.SCREEN_HEIGHT;
        x = x < 0 ? x + AsteroidsGame.SCREEN_WIDTH : x;
        y = y < 0 ? y + AsteroidsGame.SCREEN_HEIGHT : y;
    }

    public void rotate_left() {
        this.degrees += -Math.PI/35;
    }

    public void rotate_right() {this.degrees += Math.PI/35;}

    public void move() {
            Rectangle newBox = new Rectangle((int) (x + x1 - AsteroidsGame.PLAYER_WIDTH * SHIP_SCALE + 2), (int) (y + y1 - (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6) + AsteroidsGame.PLAYER_HEIGHT -10)) , 2 * AsteroidsGame.PLAYER_WIDTH * Player.SHIP_SCALE -2, (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6)) + AsteroidsGame.PLAYER_HEIGHT + 6);
            x += x1;
            y += y1;
            hitBox = newBox;
        //实现玩家从屏幕一端出现到另一端的操作
        x %= AsteroidsGame.SCREEN_WIDTH;   //从x，y到最大值的地方返回最小值的地方
        y %= AsteroidsGame.SCREEN_HEIGHT;
        x = x < 0 ? x + AsteroidsGame.SCREEN_WIDTH : x;
        y = y < 0 ? y + AsteroidsGame.SCREEN_HEIGHT : y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
