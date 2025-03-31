package ccprog3_mco;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMovement extends MouseAdapter {
    public int x, y;
    public boolean mousePressed;
    
    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        updatePosition(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        updatePosition(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
        updatePosition(e);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        updatePosition(e);
    }
    
    private void updatePosition(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}