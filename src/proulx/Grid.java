package proulx;

import javafx.util.Pair;

import java.awt.*;
import java.util.Random;

public class Grid implements Drawable{
    public static Grid instance = new Grid();

    private Rectangle[][] gridValues;
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 21;
    private Pair<Integer, Integer> fruitLocation;

    private Grid() {
        //Private - singleton
        gridValues = new Rectangle[GRID_HEIGHT][GRID_WIDTH];

        for (int i = 1; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                gridValues[i][j] = new Rectangle(i * 16, j * 16, 16, 16); //Initialize to default value
            }
        }

        Random r = new Random();
        int randomX = r.nextInt(GRID_WIDTH - 1) + 1;
        int randomY = r.nextInt(GRID_HEIGHT - 1) + 1;

        fruitLocation = new Pair<>(randomX, randomY);

    }

    public Pair<Integer, Integer> getFruitLocation() {
        return fruitLocation;
    }

    public void regenerateFruit(int x, int y) {
        fruitLocation = new Pair<>(x, y);
    }

    public void draw(Graphics g) {
        for (int i = 1; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {

                if (new Pair<>(j, i).equals(fruitLocation)) {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }

                g.fillRect(16 * j, 16 * i, 16, 16);
            }
        }
    }
}
