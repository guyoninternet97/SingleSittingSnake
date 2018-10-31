package proulx;

import java.awt.*;

public class ScoreboardHandler implements Drawable, Updateable{
    private int score;
    private String header;

    public static final ScoreboardHandler instance = new ScoreboardHandler();

    private ScoreboardHandler() {
        //singleton
        this.score = 0;
        header = "Snake";
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.drawString(Integer.toString(this.score), 0, 10);
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public int getScore() {
        return score;
    }

    public void update() {

    }
}
