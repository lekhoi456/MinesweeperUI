import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * MouseListener is a MouseListener implemented
 * @author KhoiLQCE130023
 */
public class MouseListener extends MouseAdapter {
    private final GamePanel gamePanel;
    private final int row;
    private final int col;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean bothPressed;

    // constructor
    public MouseListener(GamePanel gamePanel, int row, int col)
    {
        this.gamePanel = gamePanel;
        this.row = row;
        this.col = col;
        leftPressed = false;
        rightPressed = false;
        bothPressed = false;
    }
   
    @Override
    public void mousePressed(MouseEvent e) 
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            leftPressed = true;
        }
        else if (SwingUtilities.isRightMouseButton(e))
        {
            rightPressed = true;
        }

        if (leftPressed && rightPressed)
        {
            bothPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            leftPressed = false;
            if (bothPressed)
            {
                if (!rightPressed)
                {
                    bothPressed = false;
                    try {
                        gamePanel.uncoverAll(row, col);
                    } catch (Exception ex) {
                        Logger.getLogger(MouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else // only left button was pressed
            {
                try {
                    gamePanel.uncover(row, col);
                } catch (Exception ex) {
                    Logger.getLogger(MouseListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if (SwingUtilities.isRightMouseButton(e))
        {
            rightPressed = false;
            if (bothPressed)
            {
                if (!leftPressed)
                {
                    bothPressed = false;
                    try {
                        gamePanel.uncoverAll(row, col);
                    } catch (Exception ex) {
                        Logger.getLogger(MouseListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else // only right button was pressed
            {
                gamePanel.markMine(row, col);
            }
        }
    }
    
}