package proulx;

import javafx.util.Pair;

import java.awt.*;
import java.util.*;

public class Snake {
    private ArrayList<PlayerTile> snakeTiles;
    private SnakePanel storedPanel;
    private Map<Pair<Integer, Integer>, PlayerTile.DIRECTION> changePoints;

    private ArrayList<Pair<Integer, Integer>> allPossibleAreas;
    private ArrayList<Pair<Integer, Integer>> occupiedAreas;

    public Snake(SnakePanel panel) {
        storedPanel = panel;
        snakeTiles = new ArrayList<>();
        snakeTiles.add(new PlayerTile(storedPanel, 10, 10));
        changePoints = new HashMap<>();

        allPossibleAreas = new ArrayList<>();
        occupiedAreas = new ArrayList<>();

        populatePossibleAreasSet();
    }

    private void populatePossibleAreasSet() {
        for (int i = 0; i < Grid.GRID_HEIGHT; i++) {
            for (int j = 0; j < Grid.GRID_WIDTH; j++) {
                allPossibleAreas.add(new Pair<>(j, i));
            }
        }
    }

    public void update() {

        for (PlayerTile tile : snakeTiles) {
            occupiedAreas.add(new Pair<>(tile.x, tile.y)); //populate occupied area set
        }

        for (PlayerTile tile : snakeTiles) {
            tile.update();
        }

        for (PlayerTile tile : snakeTiles) {
            for (PlayerTile tile2 : snakeTiles) {
                if (tile.x == tile2.x && tile.y == tile2.y && tile != tile2) {
                    System.exit(0);
                }
            }
        }

        updateDirections();

        Pair<Integer, Integer> headLocationPair = new Pair<>(getHead().x, getHead().y);
        if (headLocationPair.equals(Grid.instance.getFruitLocation())) {
            ArrayList<Pair<Integer, Integer>> possibleAreasCopy = (ArrayList<Pair<Integer, Integer>>)allPossibleAreas.clone();
            possibleAreasCopy.removeAll(occupiedAreas);

            Random rnd = new Random();
            int xLocation = occupiedAreas.get(rnd.nextInt(occupiedAreas.size() - 1) + 1).getKey();
            int yLocation = occupiedAreas.get(rnd.nextInt(occupiedAreas.size() - 1) + 1).getValue();
            Grid.instance.regenerateFruit(xLocation, yLocation);

            addTile();
        }

    }

    public void addChangePoint(Pair<Integer, Integer> pair, PlayerTile.DIRECTION direction) {
        changePoints.put(pair, direction);
    }

    public void updateDirections() {
        Set<Pair<Integer, Integer>> locationsToRemove = new HashSet<>();
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
