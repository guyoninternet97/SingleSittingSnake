package proulx;

import javax.swing.*;
import java.awt.*;

public class SnakeTestMain {
    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Snake");
        myFrame.setSize(400, 400);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakePanel panel = new SnakePanel();

        myFrame.add(panel);
        myFrame.setVisible(true);
        KeyboardController controller = new KeyboardController(panel);
        myFrame.addKeyListener(controller);
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.isLocked = true;
            panel.update();
            controller.isLocked = false;

            myFrame.update(myFrame.getGraphics());
        }
    }
}
