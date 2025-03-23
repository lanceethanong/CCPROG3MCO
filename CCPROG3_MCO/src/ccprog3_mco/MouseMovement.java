/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ccprog3_mco;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 *
 * @author cresc
 */
public class MouseMovement extends MouseAdapter{
    
    public int x,y;
    public boolean mousePressed;
    
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = e.getX();
        int newY = e.getY();

        if (Math.abs(newX - x) > 3 || Math.abs(newY - y) > 3) { // Reduce frequent updates
            x = newX;
            y = newY;
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }
    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();      
    }
}
