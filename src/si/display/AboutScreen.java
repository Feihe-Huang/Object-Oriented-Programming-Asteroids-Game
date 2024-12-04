package si.display;

import si.model.AsteroidsGame;

import javax.swing.*;
import java.awt.*;

public class AboutScreen extends JPanel {
    private static final long serialVersionUID = -1264717778772722118L;

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
        g.setColor(Color.CYAN);
        drawString(g, "Asteroids Controls", new Rectangle(0, 0, AsteroidsGame.SCREEN_WIDTH, 64), 36);
        g.drawString("Rotate Left", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 0 * 32);
        g.drawString("left arrow", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 0 * 32);
        g.drawString("Rotate Right", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 1 * 32);
        g.drawString("right arrow", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 1 * 32);
        g.drawString("Fire", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 2 * 32);
        g.drawString("space bar", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 2 * 32);
        g.drawString("Apply Thrust", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 3 * 32);
        g.drawString("up arrow", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 3 * 32);
        g.drawString("Hyperspace Jump", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 4 * 32);
        g.drawString("shift", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 4 * 32);
        g.drawString("Emergency Stop", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 5 * 32);
        g.drawString("z", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 5 * 32);
        g.drawString("Partially Unbeatable", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 6 * 32);
        g.drawString("x", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 6 * 32);
        g.drawString("Play/Pause", 1 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 7 * 32);
        g.drawString("p", 4 * AsteroidsGame.SCREEN_WIDTH / 6, 96 + 7 * 32);
        drawString(g, "Press 'M' to return to the Main Menu", new Rectangle(0, 416, AsteroidsGame.SCREEN_WIDTH, 96), 24);
    }

    private void drawString(Graphics g, String text, Rectangle rect, int size) {
        Graphics2D g2d = (Graphics2D) g.create();

        Font font = new Font("Arial", Font.BOLD, size);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();

        g2d.setColor(Color.GREEN);
        g2d.drawString(text, x, y);
    }
}
