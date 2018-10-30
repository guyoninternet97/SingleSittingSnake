package proulx;


import java.awt.*;

public class PlayerTile {

    public int x;
    public int y;
    private SnakePanel panel;
    public DIRECTION direction;

    enum DIRECTION {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    public PlayerTile(SnakePanel panel, int x, int y) {
        this.x = x;
        this.y = y;
        this.panel = panel;
        this.direction = DIRECTION.RIGHT;
    }

    public PlayerTile(PlayerTile previousTile) {
        this.direction = previousTile.direction;
        if (previousTile.direction == DIRECTION.LEFT) {
            this.x = previousTile.x + 1;
            this.y = previousTile.y;
        } else if (previousTile.direction == DIRECTION.RIGHT) {
            this.x = previousTile.x - 1;
            this.y = previousTile.y;
        } else if (previousTile.direction == DIRECTION.DOWN) {
            this.x = previousTile.x;
            this.y = previousTile.y - 1;
        } else {
            this.x = previousTile.x;
            this.y = previousTile.y + 1;
        }
    }


    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x * 16, y * 16, 16, 16);
    }

    public void update() {
        if (direction.equals(DIRECTION.LEFT)) {
            x--;
        } else if (direction.equals(DIRECTION.RIGHT)) {
            x++;
        } else if (direction.equals(DIRECTION.UP)) {
            y--;
        } else if (direction.equals(DIRECTION.DOWN)) {
            y++;
        }

        if (x < 0 || x > Grid.GRID_WIDTH || y < 0 || y > Grid.GRID_HEIGHT) {
            System.exit(0);
        }
    }

}
