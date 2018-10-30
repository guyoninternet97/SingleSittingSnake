package proulx;

import javafx.util.Pair;

import java.awt.event.*;

public class KeyboardController implements KeyListener {
    //IsLocked prevents inputs operating faster than my update functions handle
    public boolean isLocked;
    private SnakePanel attachedPanel;

    public KeyboardController(SnakePanel panel) {
        isLocked = false;
        this.attachedPanel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        }

        if (!isLocked) {
            isLocked = true;
            if (e.getKeyCode() == KeyEvent.VK_LEFT && attachedPanel.getHead().direction != PlayerTile.DIRECTION.RIGHT) {
                attachedPanel.getHead().direction = PlayerTile.DIRECTION.LEFT;
                attachedPanel.getSnake().addChangePoint(new Pair<>(attachedPanel.getHead().x, attachedPanel.getHead().y), PlayerTile.DIRECTION.LEFT);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && attachedPanel.getHead().direction != PlayerTile.DIRECTION.LEFT) {
                attachedPanel.getHead().direction = PlayerTile.DIRECTION.RIGHT;
                attachedPanel.getSnake().addChangePoint(new Pair<>(attachedPanel.getHead().x, attachedPanel.getHead().y), PlayerTile.DIRECTION.RIGHT);
            } else if (e.getKeyCode() == KeyEvent.VK_UP && attachedPanel.getHead().direction != PlayerTile.DIRECTION.DOWN) {
                attachedPanel.getHead().direction = PlayerTile.DIRECTION.UP;
                attachedPanel.getSnake().addChangePoint(new Pair<>(attachedPanel.getHead().x, attachedPanel.getHead().y), PlayerTile.DIRECTION.UP);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN && attachedPanel.getHead().direction != PlayerTile.DIRECTION.UP) {
                attachedPanel.getHead().direction = PlayerTile.DIRECTION.DOWN;
                attachedPanel.getSnake().addChangePoint(new Pair<>(attachedPanel.getHead().x, attachedPanel.getHead().y), PlayerTile.DIRECTION.DOWN);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                attachedPanel.getSnake().addTile();
            }
        } else {
            System.out.println("lock!");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //isLocked = false;
    }
}
