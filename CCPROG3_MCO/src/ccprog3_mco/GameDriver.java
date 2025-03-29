/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ccprog3_mco;
import javax.swing.JFrame;
/**
 *
 * @author cresc
 */

public class GameDriver {
	public static void main(String[] args) 
	{
        JFrame window = new JFrame("Jungle King");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        /*
        Game game = new Game();
        game.startGame();
        */
    }
}
