package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AsteroidsGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    public static final int PLAYER_HEIGHT = 10;   //15
    public static final int PLAYER_WIDTH = 4;      //6
    //写player时要用到
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<EnemyShip> enemyShips;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    private Player player;
    public static List<AlienShips> alienShips = new ArrayList<>(2);   //本来不是static
    private Level[] level;
    int currentLevel = 0;
    Random random = new Random();

    public AsteroidsGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;

    }

    @Override
    public int getLives() {
        return playerLives + getPlayerScore() / 3000;
    }

    @Override
    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }


    @Override
    public void updateGame() {
        if (!isPaused()) {
            player.tick();
            targets.clear();
            targets.addAll(level[currentLevel].getHittable());
            targets.add(player);
            targets.addAll(alienShips);
            playerBullets();
            enemyBullets();
            enemyBullets.addAll(level[currentLevel].move());
            movePlayer();
            asteroidsHit();
            createAlienShips();
        }
    }

    public void createAlienShips() {
        if (random.nextInt() % 200 == 0) {
            int max_x = AsteroidsGame.SCREEN_WIDTH;
            int max_y = AsteroidsGame.SCREEN_HEIGHT;
            Random ran = new Random();
            double judge = ran.nextInt(4);
            int x = 0;
            int y = 0;
            if (judge == 0) {
                x = ran.nextInt(max_x); // 出现的地方的x坐标
                y = 0;
            } else if (judge == 1) {
                x = 0;
                y = ran.nextInt(max_x); // 出现的地方的x坐标
            } else if (judge == 2) {
                x = ran.nextInt(max_x); // 出现的地方的x坐标
                y = max_y;
            } else if (judge == 3) {
                x = max_x;
                y = ran.nextInt(max_y); // 出现的地方的x坐标
            }

            double h = Math.random() * 2 * Math.PI;
            double moveX = 1 * Math.sin(h) / 3;
            double moveY = 1 * Math.cos(h) / 3;

            alienShips.add(new AlienShips(x, y, moveX, moveY));
        }
    }

    private void movePlayer() {
        if (listener.hasPressedFire()) {
            Bullet b = player.fire();
            if (b != null) {
                playerBullets.add(b);
                listener.resetFire();
            }
        }
        if (listener.isPressingLeft()) {
            player.rotate_left();
            player.move();
        } else if (listener.isPressingRight()) {
            player.rotate_right();
            player.move();
        } else if (listener.isPressingUp()) {
            player.move();
            if (listener.isPressingAcc()) {
                player.moveUp();
            } else if (listener.hasPressedStop()) {
                player.stop();
                listener.resetStop();
            } else if (listener.hasPressedHyperspaceJump()) {
                jump();
                listener.resetHyperspaceJump();
                player.stop();
                listener.resetStop();
            }
        } else if (listener.hasPressedHyperspaceJump()) {
            jump();
            listener.resetHyperspaceJump();
            player.stop();
            listener.resetStop();
        } else if (listener.hasPressedStop()) {
            player.stop();
            listener.resetStop();
        }
    }

    public void jump() {
        Random r = new Random();

        boolean found = false;
        while (!found) {
            int x = r.nextInt(AsteroidsGame.SCREEN_WIDTH);
            int y = r.nextInt(AsteroidsGame.SCREEN_HEIGHT);
            Rectangle a = new Rectangle(x - AsteroidsGame.PLAYER_WIDTH * 3 + 2, y - (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6) + AsteroidsGame.PLAYER_HEIGHT - 10), 2 * AsteroidsGame.PLAYER_WIDTH * Player.SHIP_SCALE - 2, (int) (AsteroidsGame.PLAYER_HEIGHT / Math.tan(Math.PI / 6)) + AsteroidsGame.PLAYER_HEIGHT + 6);

            found = true;
            for (EnemyShip enemyShip : enemyShips) {
                found = !a.intersects(enemyShip.getHitBox()) && found;
            }

            if (found) {
                player.x = x;
                player.y = y;
            }
        }
    }

    private void getMedShips(EnemyShip e){
        Random ran = new Random();
        for (int i = 0; i < 2; i++) {
            int judge = ran.nextInt(4);
            double h_x = Math.random() * Math.toRadians(20) + Math.toRadians(10);
            double mx = 0.4 * Math.sin(h_x) + 0.5;
            double my = 0.4 * Math.cos(h_x) + 0.5;
            if (judge == 0 ) {
                double moveX = mx;
                double moveY = my;    //出现在被打碎陨石的地方
                EnemyShip med = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Med);
                level[currentLevel].getHittable().add(med);
                level[currentLevel].asteroids.ships.add(med);
            } else if (judge == 1) {
                double moveX = -(mx);
                double moveY = my;    //出现在被打碎陨石的地方
                EnemyShip med = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Med);
                level[currentLevel].getHittable().add(med);
                level[currentLevel].asteroids.ships.add(med);
            } else if (judge == 2) {
                double moveX = -(mx);
                double moveY = -(my);    //出现在被打碎陨石的地方
                EnemyShip med = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Med);
                level[currentLevel].getHittable().add(med);
                level[currentLevel].asteroids.ships.add(med);
            } else {
                double moveX = mx;
                double moveY = -(my);    //出现在被打碎陨石的地方
                EnemyShip med = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Med);
                level[currentLevel].getHittable().add(med);
                level[currentLevel].asteroids.ships.add(med);
            }
        }
    }

    private void getSmaShips(EnemyShip e){
        Random ran = new Random();
        for (int i = 0; i < 2; i++) {
            int judge = ran.nextInt(4);
            double h_x = Math.random() * Math.toRadians(20) + Math.toRadians(10);
            double mx = 0.8 * Math.sin(h_x) + 1;
            double my = 0.8 * Math.cos(-h_x) + 1;
            if (judge == 0 ) {
                double moveX = mx;
                double moveY = my;    //出现在被打碎陨石的地方
                EnemyShip sma = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Sma);
                level[currentLevel].getHittable().add(sma);
                level[currentLevel].asteroids.ships.add(sma);
            } else if (judge == 1) {
                double moveX = -(mx);
                double moveY = my;    //出现在被打碎陨石的地方
                EnemyShip med = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Sma);
                level[currentLevel].getHittable().add(med);
                level[currentLevel].asteroids.ships.add(med);
            } else if (judge == 2) {
                double moveX = -(mx);
                double moveY = -(my);    //出现在被打碎陨石的地方
                EnemyShip sma = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Sma);
                level[currentLevel].getHittable().add(sma);
                level[currentLevel].asteroids.ships.add(sma);
            } else {
                double moveX = mx;
                double moveY = -(my);    //出现在被打碎陨石的地方
                EnemyShip sma = new EnemyShip(e.getX(), e.getY(), moveX, moveY, AlienType.Sma);
                level[currentLevel].getHittable().add(sma);
                level[currentLevel].asteroids.ships.add(sma);
            }
        }
    }


    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (Bullet playerBullet : playerBullets) {
            if (playerBullet.isAlive() && playerBullet.getHitBox().intersects(SCREEN_BOUNDS)) {
                playerBullet.move();
                for (Hittable t : targets) {
                    if (t == player) {
                        if (t.isHit(playerBullet)) {
                            playerScore += t.getPoints();
                            playerBullet.destroy();
                        }
                    } else if (t != playerBullet && t != player) {
                        if (t.isHit(playerBullet)) {
                            if (t.getAlienType() == AlienType.Lar && t instanceof EnemyShip) {
                                getMedShips((EnemyShip) t);
                                playerScore += t.getPoints();
                                playerBullet.destroy();
                            } else if (t.getAlienType() == AlienType.Med && t instanceof EnemyShip) {
                                getSmaShips((EnemyShip) t);
                                playerScore += t.getPoints();
                                playerBullet.destroy();
                            } else if (t.getAlienType() == AlienType.Sma) {
                                playerScore += t.getPoints();
                                playerBullet.destroy();
                            }
                            for (int i = 0; i < alienShips.size(); i++) {
                                if (t == alienShips.get(i)) {
                                    playerScore += t.getPoints();
                                    playerBullet.destroy();
                                }
                            }
                        }
                    }
                }
            } else {
                remove.add(playerBullet);
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        List<Bullet> remove = new ArrayList<>();
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet b = enemyBullets.get(i);
            if (b.isAlive() && b.getHitBox().intersects(SCREEN_BOUNDS)) {
                b.move();
                for (Hittable t : targets) {
                    if (t != b) {
                        if (t.isHit(b)) {
                            if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                            } else if (t.isEnemyShip() && t.getAlienType() == AlienType.Lar) {
                                getMedShips((EnemyShip) t);
                            } else if (t.getAlienType() == AlienType.Med && t instanceof EnemyShip) {
                                getSmaShips((EnemyShip) t);
                            }
                            b.destroy();
                        }
                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }

    private void asteroidsHit() {
        enemyShips = getEnemyShips();
        List<EnemyShip> remove = new ArrayList<>();
        for (int i = 0; i < enemyShips.size(); i++) {
            EnemyShip a = enemyShips.get(i);
            if (a.isAlive() && a.isHitPlayer(player)) {
                if (a.getAlienType() == AlienType.Lar) {
                    getMedShips(a);
                } else if (a.getAlienType() == AlienType.Med) {
                    getSmaShips(a);
                }
                for (Hittable t : targets) {
                    if (t != a) {
                        if (t.isHitPlayer(player)) {
                            if (t.isPlayer()) {
                                level[currentLevel].move();      //为了使陨石先消失游戏再暂停而调用，不确保不会出错
                                playerLives--;
                                pause = true;
                                player.stop();
                                listener.resetStop();
                            }
                        }
                    }
                }
            }
            for (int k = 0; k < alienShips.size(); k++) {
                if (a.isAlive() && a.isHitAlienShips(alienShips.get(k))) {
                    if (a.getAlienType() == AlienType.Lar) {
                        getMedShips(a);
                    } else if (a.getAlienType() == AlienType.Med) {
                        getSmaShips(a);
                    }
                    alienShips.get(k).setAlive(false);
                }
            }
        }
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        player = new Player(listener);                   //实现此处才可显示player
        alienShips = new ArrayList<>();
        level = new Level[1000];
        for (int i = 0; i < 1000; i++) {
            level[i] = new Level( i + 4, this);
        }
    }

    @Override
    public boolean isLevelFinished() {
            int noShips = level[currentLevel].getShipsRemaining();  //查看还剩多少需消灭的敌军
            return noShips == 0;
    }

    @Override
    public int getTargetFPS() {
        return 0;
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public void moveToNextLevel() {
        pause = true;
        currentLevel++;
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        alienShips = new ArrayList<>();
        player.stop();
        listener.resetStop();
    }

    @Override
    public boolean isGameOver() {
        return !(getLives() > 0);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getShip() {
        return player;
    }

    public List<AlienShips> getAlienShips() {
        return alienShips;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<EnemyShip> getEnemyShips() {
        return level[currentLevel].getEnemyShips();
    }

}
