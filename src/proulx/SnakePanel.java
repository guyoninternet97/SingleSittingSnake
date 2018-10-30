package proulx;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SnakePanel extends JPanel {

    private ArrayList<Rectangle> objects;
    private Grid grid;
    private Snake snake;

    public SnakePanel() {
        super();
        objects = new ArrayList<>();
        grid = Grid.instance;
        snake = new Snake(this);
    }

    public Snake getSnake() {
        return this.snake;
    }

    public void update() {
        snake.update();
    }

    public void paint(Graphics g) {
        grid.draw(g);
        snake.draw(g);
    }

    public PlayerTile getHead() {
        return snake.getHead();
    }

}
