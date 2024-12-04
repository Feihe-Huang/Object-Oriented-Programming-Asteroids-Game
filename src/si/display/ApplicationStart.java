package si.display;

import si.database.PersistentScoreKeeper;
import si.model.AsteroidsGame;
import ucd.comp2011j.engine.GameManager;
import javax.swing.*;

public class ApplicationStart {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setSize(AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT); //mainWindow size
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setTitle("Asteroids");
        mainWindow.setLocationRelativeTo(null);  //Make the main screen appear in the middle

        PlayerListener playerListener = new PlayerListener();
        mainWindow.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        mainWindow.addKeyListener(menuListener);
        AsteroidsGame game = new AsteroidsGame(playerListener);
        GameScreen gameScreen = new GameScreen(game, playerListener);
        MenuScreen menuScreen = new MenuScreen();
        PersistentScoreKeeper scoreKeeper = new PersistentScoreKeeper();
        GameManager gameManager = new GameManager(game, mainWindow, menuListener, menuScreen, new AboutScreen(), new ScoreScreen(scoreKeeper), gameScreen, scoreKeeper);
        mainWindow.setVisible(true);  //Make windows visible
        gameManager.run();
    }
}
