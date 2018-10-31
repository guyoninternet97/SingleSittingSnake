package proulx;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SnakePanel extends JPanel {

    private ArrayList<Drawable> itemsToDraw;
    private ArrayList<Updateable> itemsToUpdate;
    Snake snake;

    public SnakePanel() {
        super();
        itemsToDraw = new ArrayList<>();
        itemsToUpdate = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        snake = Snake.instance;
        snake.setStoredPanel(this);
        itemsToUpdate.add(snake);
        itemsToUpdate.add(ScoreboardHandler.instance);
        itemsToDraw.add(Grid.instance);
        itemsToDraw.add(snake);
        itemsToDraw.add(ScoreboardHandler.instance);


    }

    public Snake getSnake() {
        Snake returnSnake = null;

        for (Updateable item : itemsToUpdate) {
            if (item instanceof Snake) {
                returnSnake = (Snake) item;
            }
        }

        return returnSnake;
    }

    public void update() {
        for (Updateable item : itemsToUpdate) {
            item.update();
        }

    }

    public void paint(Graphics g) {
        for (Drawable item : itemsToDraw) {
            item.draw(g);
        }
    }

    public PlayerTile getHead() {
        PlayerTile returnTile = null;

        for (Updateable item : itemsToUpdate) {
            if (item instanceof Snake) {
                Snake snake = (Snake) item;
                returnTile = snake.getHead();
            }
        }

        return returnTile;
    }

}
