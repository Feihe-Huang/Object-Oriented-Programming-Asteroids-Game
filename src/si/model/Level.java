package si.model;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    public Asteroids asteroids;
    int aNum;
    private AsteroidsGame game;


    public Level(int Num, AsteroidsGame g) {
        game = g;
        aNum = Num;
        reset();
    }

    //查看还剩多少需消灭的敌军
    public int getShipsRemaining() {
        return asteroids.getShipsRemaining();
    }

    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(asteroids.getHittable());
        return targets;
    }

    //控制敌军发射子弹
    public List<Bullet> move() {
        asteroids.move();
        for (int i = 0; i < game.alienShips.size(); i++) {
            game.alienShips.get(i).move();
        }
        List<AlienShips> alienShips = game.alienShips;
        List<Bullet> eBullets = new ArrayList<>();
        for (AlienShips s : alienShips) {                        //不会是ships   改成外星飞船
            Bullet b = s.fire();
            if (b != null) {
                eBullets.add(b);
            }
        }
        return eBullets;
    }

    public List<EnemyShip> getEnemyShips() {
        return asteroids.getEnemyShips();
    }

    public void reset() {
        asteroids = new Asteroids(aNum, game);
    }

}
