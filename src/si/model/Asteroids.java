package si.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//注意变量名可能冲突
public class Asteroids {
    public List<EnemyShip> ships;
    public List<EnemyShip> medShips;
    private AsteroidsGame game;
    //不确定是否可以使其作为陨石坐标
    int xa;
    int ya;
    int num;
    public double h;


    public Asteroids(int aNum, AsteroidsGame g) {  //改变参数
        game = g;
        num = aNum;
        ships = new ArrayList<EnemyShip>();
        medShips = new ArrayList<EnemyShip>();    //必须初始化使得medShips   size = 0（而不是medShip = null）
        for (int i = 0; i < aNum; i++) {
            ships.add(getRandomAsteroids(AlienType.Lar));
        }
    }

    public EnemyShip getRandomAsteroids(AlienType type){
        int max_x = AsteroidsGame.SCREEN_WIDTH;
        int max_y = AsteroidsGame.SCREEN_HEIGHT;
        Random ran = new Random();
        int judge = ran.nextInt(4);
        boolean judge_x = ran.nextBoolean();
        boolean judge_y = ran.nextBoolean();
        if (judge_x) {
            xa = ran.nextInt(max_x / 3); // 出现的地方的x坐标
        } else {
            xa = ran.nextInt( max_x / 3) + 2 * max_x / 3; // 出现的地方的x坐标
        }
        if (judge_y) {
            ya = ran.nextInt(max_y / 3); // 出现的地方的x坐标
        } else {
            ya = ran.nextInt( max_y / 3) + 2 * max_y / 3; // 出现的地方的x坐标
        }
        h = Math.random() * 2 * Math.PI;
        if (type == AlienType.Lar) {
            if(judge == 0) {
                double moveX = 1 * Math.sin(h) / 5 + 0.3;
                double moveY = 1 * Math.cos(h) / 5 + 0.3;
                return new EnemyShip(xa, ya, moveX, moveY, type);
            } else if (judge == 1){
                double moveX = -(1 * Math.sin(h) / 5 + 0.3);
                double moveY = -(1 * Math.cos(h) / 5 + 0.3);
                return new EnemyShip(xa, ya, moveX, moveY, type);
            } else if (judge == 2){
                double moveX = (1 * Math.sin(h) / 5 + 0.3);
                double moveY = -(1 * Math.cos(h) / 5 + 0.3);
                return new EnemyShip(xa, ya, moveX, moveY, type);
            } else{
                double moveX = -(1 * Math.sin(h) / 5 + 0.3);
                double moveY = (1 * Math.cos(h) / 5 + 0.3);
                return new EnemyShip(xa, ya, moveX, moveY, type);
            }
        } else {
            double moveX = 1 * Math.sin(h) / 5 + 0.3;
            double moveY = 1 * Math.cos(h) / 5 + 0.3;
            return new EnemyShip(xa, ya, moveX, moveY, type);
        }
    }

    public List<Hittable> getHittable() {
        return new ArrayList<>(ships);
    }

    public void move() {
        List<EnemyShip> remove = new ArrayList<EnemyShip>();
        for (EnemyShip s : ships) {
            if (!s.isAlive()) {
                remove.add(s);
            }
        }
        ships.removeAll(remove);        //若敌军被打死就把他们放到这里并且全部消除

        List<EnemyShip> s = new ArrayList<EnemyShip>(ships);
        for (EnemyShip ship : s) {
            ship.x %= AsteroidsGame.SCREEN_WIDTH;   //从x，y到最大值的地方返回最小值的地方
            ship.y %= AsteroidsGame.SCREEN_HEIGHT;

            //从x，y到最小值的地方返回最大值的地方
            ship.x = ship.x < 0 ? ship.x + AsteroidsGame.SCREEN_WIDTH : ship.x;
            ship.y = ship.y < 0 ? ship.y + AsteroidsGame.SCREEN_HEIGHT : ship.y;
        }
        for (int i = 0; i < ships.size(); i ++){
            s.get(i).move();
        }
    }

    //用于画出enemy ships
    public List<EnemyShip> getEnemyShips() {
        return new ArrayList<EnemyShip>(ships);
    }

    public int getXa(){
        return xa;
    }

    public int getYa(){
        return ya;
    }

    //查看还剩多少需消灭的敌军
    public int getShipsRemaining() {
        return ships.size();
    }
}