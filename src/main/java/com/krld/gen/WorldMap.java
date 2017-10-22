package com.krld.gen;

import java.awt.*;

public class WorldMap {
    public static final int OBSTACLE = 1;
    public static final int FREE = 0;

    private final int[] array;

    public final int width;
    public final int height;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width * height];
    }

    public int get(Point point) {
        int x = point.x;
        int y = point.y;

        return get(x, y);
    }

    private int getIndex(Point point) {
        return getIndex(point.x, point.y);
    }

    private int getIndex(int x, int y) {
        return x * height + y;
    }

    public void addObstacleTriple(Point point) {

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                safeSet(point.x + x - 1, point.y + y - 1, OBSTACLE);
            }
        }

    }

    private void safeSet(int x, int y, int value) {
        if (isNotInBounds(x, y)) {
            return;
        }
        array[getIndex(x, y)] = value;
    }

    public int get(int x, int y) {
        if (isNotInBounds(x, y)) {
            return OBSTACLE;
        }
        return array[getIndex(x, y)];
    }

    private boolean isNotInBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }
}
