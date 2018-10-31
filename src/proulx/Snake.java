package proulx;

import javafx.util.Pair;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;

public class Snake implements Drawable, Updateable{
    private ArrayList<PlayerTile> snakeTiles;
    private SnakePanel storedPanel;
    //A map which keeps track of points that body tiles need th change direction on, and what direction
    private Map<Pair<Integer, Integer>, PlayerTile.DIRECTION> changePoints;

    //Array lists to keep track of points occupied b the snake
    private ArrayList<Pair<Integer, Integer>> allPossibleAreas;
    private ArrayList<Pair<Integer, Integer>> occupiedAreas;

    public static final Snake instance = new Snake();

    private Snake() {
        //Initialize stuff
        snakeTiles = new ArrayList<>();
        snakeTiles.add(new PlayerTile(storedPanel, 10, 10));
        changePoints = new HashMap<>();

        allPossibleAreas = new ArrayList<>();
        occupiedAreas = new ArrayList<>();

        populatePossibleAreasSet();
    }

    public void setStoredPanel(SnakePanel panel) {
        this.storedPanel = panel;
    }

    private void populatePossibleAreasSet() {
        for (int i = 1; i < Grid.GRID_HEIGHT; i++) {
            for (int j = 0; j < Grid.GRID_WIDTH; j++) {
                allPossibleAreas.add(new Pair<>(j, i));
            }
        }
    }
    @SuppressWarnings("unchecked") //the casting is fine get off my back
    public void update() {
        occupiedAreas.clear();

        //Populate the points occupied by the snake
        for (PlayerTile tile : snakeTiles) {
            occupiedAreas.add(new Pair<>(tile.x, tile.y)); //populate occupied area set
        }


        for (PlayerTile tile : snakeTiles) {
            //Update the body tiles
            tile.update();

            //Ensure the head hasn't run into a body tile
            if (tile != snakeTiles.get(0) && tile.x == snakeTiles.get(0).x && tile.y == snakeTiles.get(0).y) {
                System.exit(0);
            }
        }


        updateDirections();

        //This handles when you get a fruit ---------------
        //Initialize a pair for the headlocation
        Pair<Integer, Integer> headLocationPair = new Pair<>(getHead().x, getHead().y);

        //If you've gotten the fruit
        if (headLocationPair.equals(Grid.instance.getFruitLocation())) {
            //Make a copy of the possibleAreas
            ScoreboardHandler.instance.setScore(ScoreboardHandler.instance.getScore() + 1);

            ArrayList<Pair<Integer, Integer>> possibleAreasCopy = (ArrayList<Pair<Integer, Integer>>)allPossibleAreas.clone();

            //Find the areas on the board which are not occupied
            possibleAreasCopy.removeAll(occupiedAreas);

            //Generate new X and Y using possible remaining values
            Random rnd = new Random();
            int xLocation = possibleAreasCopy.get(rnd.nextInt(possibleAreasCopy.size() - 1) + 1).getKey();
            int yLocation = possibleAreasCopy.get(rnd.nextInt(possibleAreasCopy.size() - 1) + 1).getValue();
            Grid.instance.regenerateFruit(xLocation, yLocation);

            addTile();
        }

    }

    //Add a point that, when body tiles pass over, need to change direction
    public void addChangePoint(Pair<Integer, Integer> pair, PlayerTile.DIRECTION direction) {
        changePoints.put(pair, direction);
    }

    //This is in charge of the body tiles turning
    private void updateDirections() {
        //Keep track of the turnpoints to remove
        Set<Pair<Integer, Integer>> locationsToRemove = new HashSet<>();
        //Check the body tiles against the turnpoints
        //TODO: comment this better
        for (PlayerTile tile : snakeTiles) {
            for (Pair<Integer, Integer> location : changePoints.keySet()) {
                if (tile.x == location.getKey() && tile.y == location.getValue()) {
                    tile.direction = changePoints.get(location);
                    if (tile.equals(snakeTiles.get(snakeTiles.size() - 1))) {
                        locationsToRemove.add(location);
                    }
                }
            }
        }
        boolean clearChangePoints = true;
        PlayerTile.DIRECTION headDirection = snakeTiles.get(0).direction;

        for (PlayerTile tile : snakeTiles) {
            if (!tile.direction.equals(headDirection)) {
                clearChangePoints = false;
            }
        }

        if (clearChangePoints) {
            changePoints.clear();
        }

        for (Pair<Integer, Integer> location : locationsToRemove) {
            changePoints.remove(location);
        }
        locationsToRemove.clear();
    }

    public void draw(Graphics g) {
        for(PlayerTile tile : snakeTiles) {
            tile.draw(g);
        }
    }

    public void addTile() {
        snakeTiles.add(new PlayerTile(snakeTiles.get(snakeTiles.size() - 1)));
    }

    public PlayerTile getHead() {
        return snakeTiles.get(0);
    }

}
