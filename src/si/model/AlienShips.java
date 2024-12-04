package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlienShips implements Hittable{
    private boolean alive;
    private String name;
    public double x;
    public double y;
    private Random rand;
    double rX, rY;

    public AlienShips(int x, int y, double rX, double rY) {
        this.x = x;
        this.y = y;
        this.rX = rX;
        this.rY = rY;
        this.rand = new Random(x * 100 + y);
        this.alive = true;
    }

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

    @Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x + 2 , (int) y + 2, 22, 20);
    }

    @Override
    public AlienType getAlienType() {
        return null;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 200;
    }

    public boolean isPlayer() {
        return false;
    }

    public boolean isEnemyShip() {
        return true;
    }

    public void movePosition() {
        x += rX ;
        y += rY ;
    }

    public void move() {
        List<AlienShips> remove = new ArrayList<AlienShips>();
        if (AsteroidsGame.alienShips != null) {
            for (AlienShips a : AsteroidsGame.alienShips) {
                if (!a.isAlive()) {
                    remove.add(a);
                }
            }
            AsteroidsGame.alienShips.removeAll(remove);//若敌军被打死就把他们放到这里并且全部消除
            List<AlienShips> s = new ArrayList<>(AsteroidsGame.alienShips);

            for (AlienShips alienShips : s) {
                alienShips.x %= AsteroidsGame.SCREEN_WIDTH;   //从x，y到最大值的地方返回最小值的地方
                alienShips.y %= AsteroidsGame.SCREEN_HEIGHT;
                //从x，y到最小值的地方返回最大值的地方
                alienShips.x = alienShips.x < 0 ? alienShips.x + AsteroidsGame.SCREEN_WIDTH : alienShips.x;
                alienShips.y = alienShips.y < 0 ? alienShips.y + AsteroidsGame.SCREEN_HEIGHT : alienShips.y;
            }
            for (int i = 0; i < AsteroidsGame.alienShips.size(); i++) {
                s.get(i).movePosition();
            }
        }
    }

    public Bullet fire() {
        Bullet bul = null;
        if (rand.nextInt() % 130 == 0 ) {      // 用于控制   随机时间    生成子弹
            int a = ((int) x + getHitBox().width);
            int b = (int) (y + getHitBox().height);
            bul = new Bullet(a - 10, b + 3, false, name, null);
        }
        return bul;
    }

     public int getX() {   //本为final
        return (int) x;
    }

     public int getY() {    //本为final
        return (int) y;
    }

    public void setAlive(boolean b){
        alive = b;
    }
}
