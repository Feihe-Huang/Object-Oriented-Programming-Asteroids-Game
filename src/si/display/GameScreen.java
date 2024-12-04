package si.display;

import si.model.*;
import si.model.Player;
import si.model.AsteroidsGame;
import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    //用处未知
    private static final long serialVersionUID = -8282302849760730222L;
    private AsteroidsGame game;
    private PlayerListener listener;

    public GameScreen(AsteroidsGame game, PlayerListener listener) {
        this.game = game;
        this.listener = listener;
    }

    private void drawPlayerShip(Graphics2D gc, Player p, double degrees) {
        int x = p.getX();
        int y = p.getY();   //(x,y) 为屏幕中心坐标
        int [] x_coords = new int[]{(int) (AsteroidsGame.PLAYER_HEIGHT *  Math.sin(degrees)),(int) (AsteroidsGame.PLAYER_WIDTH * Math.sin(30 - degrees)), (int) ((- AsteroidsGame.PLAYER_WIDTH) * Math.sin(degrees + 30))};
        int [] y_coords = new int[]{(int) ((- AsteroidsGame.PLAYER_HEIGHT) * Math.cos(degrees)), (int) (AsteroidsGame.PLAYER_WIDTH * Math.cos(30 - degrees)), (int) (AsteroidsGame.PLAYER_WIDTH * Math.cos(degrees + 30))};       //确定三角形的三个顶点， 要求把窗口中心放到中间
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Player.SHIP_SCALE;   //船大小要改
            y_adjusted[i] = y + y_coords[i] * Player.SHIP_SCALE;
        }
        int[] x_adjusted_1 = new int[x_coords.length];
        int[] y_adjusted_1 = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted_1[i] = (x + x_coords[i] * Player.SHIP_SCALE ) * 2;   //船大小要改
            y_adjusted_1[i] = (y + y_coords[i] * Player.SHIP_SCALE) * 2;
        }

        Polygon pg = new Polygon(x_adjusted, y_adjusted, x_adjusted.length);

        Polygon pg_1 = new Polygon(x_adjusted, y_adjusted, x_adjusted.length);

        gc.setColor(Color.CYAN);
        gc.drawPolygon(pg);
        gc.drawPolygon(pg_1);
        if (p.getTime() > 0 || listener.hasPressedUnbeatable()){
            gc.setColor(Color.ORANGE);
            gc.drawOval(x - 33,y - 40,67,67);
        }
//        gc.drawRect(p.getHitBox().x, p.getHitBox().y, p.getHitBox().width, p.getHitBox().height);
    }

    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.GREEN);
        gc.fillOval(b.getX(), b.getY(), b.BULLET_HEIGHT,b.BULLET_HEIGHT);
//        gc.drawRect(b.getHitBox().x, b.getHitBox().y, b.getHitBox().width, b.getHitBox().height);
//        gc.drawString(b.count + "", b.getX(), b.getY());
    }

     public void drawAsteroidsShip(Graphics2D gc, EnemyShip es) {
        if (es.getType() == AlienType.Lar) {
            drawAsteroidsA(gc, es);
        } else if (es.getType() == AlienType.Med) {
            drawAsteroidsB(gc, es);
        } else {
            drawAsteroidsC(gc, es);
        }
    }

    //陨石1
    private void drawAsteroidsA(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();

        int[] x_coords = new int[]{0, 0, 3, 8, 12, 11, 7, 2};
        int[] y_coords = new int[]{2, 7, 11, 11, 7, 4, 0, 0};
        int[] x_adjusted = new int [x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = (int) (x + x_coords[i] * EnemyShip.LAR_ASTEROIDS_SCALE );
            y_adjusted[i] = (int) (y + y_coords[i] * EnemyShip.LAR_ASTEROIDS_SCALE );
        }
        gc.setColor(Color.LIGHT_GRAY);
        gc.drawPolygon(x_adjusted, y_adjusted, x_adjusted.length);
//        gc.drawRect(es.getHitBox().x, es.getHitBox().y, es.getHitBox().width, es.getHitBox().height);
    }

    private void drawAsteroidsB(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0, 5, 15, 11, 15, 13, 8, 5, 0};
        int[] y_coords = new int[]{12, 19, 17, 11, 8, 1, 5, -2, -1};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.MED_ASTEROIDS_SCALE;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.MED_ASTEROIDS_SCALE;
        }
        gc.setColor(Color.LIGHT_GRAY);
        gc.drawPolygon(x_adjusted, y_adjusted, x_adjusted.length);
//        gc.drawRect(es.getHitBox().x, es.getHitBox().y, es.getHitBox().width, es.getHitBox().height);
    }


    private void drawAsteroidsC(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{1, 4, 5, 4, 5, 4, 1, 0};
        int[] y_coords = new int[]{0, 0, 2, 3, 4, 5, 5, 3};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.SMA_ASTEROIDS_SCALE;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.SMA_ASTEROIDS_SCALE;
        }
        gc.setColor(Color.LIGHT_GRAY);
        gc.drawPolygon(x_adjusted, y_adjusted, x_adjusted.length);
//        gc.drawRect(es.getHitBox().x, es.getHitBox().y, es.getHitBox().width, es.getHitBox().height);
    }


    private void drawAlienShips(Graphics2D gc, AlienShips as){
        if (as != null) {
            int x = as.getX();
            int y = as.getY();
            int[] x_coords = new int[]{3, 2, 0, 2, 7, 9, 0, 2, 7, 9, 7, 6};
            int[] y_coords = new int[]{0, 2, 5, 8, 8, 5, 5, 2, 2, 5, 2, 0};
            int[] x_adjusted = new int[x_coords.length];
            int[] y_adjusted = new int[y_coords.length];
            for (int i = 0; i < x_coords.length; i++) {
                x_adjusted[i] = x + x_coords[i] * (EnemyShip.MED_ASTEROIDS_SCALE - 1);
                y_adjusted[i] = y + y_coords[i] * (EnemyShip.MED_ASTEROIDS_SCALE - 1);
            }
            gc.setColor(Color.MAGENTA);
            gc.drawPolygon(x_adjusted, y_adjusted, x_adjusted.length);
//            gc.drawRect(as.getHitBox().x, as.getHitBox().y, as.getHitBox().width, as.getHitBox().height);
        }
    }


    protected void paintComponent(Graphics g) {

        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2.0f));
            g.setColor(Color.black);
            g.fillRect(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
            g.setColor(Color.green);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), AsteroidsGame.SCREEN_WIDTH / 2, 20);

            drawPlayerShip(g2, game.getShip(), game.getShip().degrees);

            if (game.getAlienShips() != null) {
                for (AlienShips a : game.getAlienShips()) {
                    drawAlienShips(g2, a);
                }
            }

            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }

            for (EnemyShip e: game.getEnemyShips()){
                drawAsteroidsShip(g2,e);
            }

            if (game.isPaused() && !game.isGameOver()) {
                g2.setStroke(new BasicStroke(2.0f));
                g.setColor(Color.YELLOW);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.WHITE);
                g.drawString("Game over ", 480, 256);
            }
        }
    }
}
