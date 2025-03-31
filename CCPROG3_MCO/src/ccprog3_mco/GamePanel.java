package ccprog3_mco;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    private final int FPS = 300;
    private Thread gameThread;
    private GameModel model;
    private GameView view;
    private GameController controller;
    
    public GamePanel() {
        model = new GameModel();
        view = new GameView(model);
        controller = new GameController(model, view);
        view.setController(controller);
        
        setLayout(new BorderLayout());
        add(view, BorderLayout.CENTER);
        
        launchGame();
    }
    
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                controller.update(); // Update game state
                view.repaint(); // Repaint the view
                delta--;
            }
        }
    }
    
}