package com.krld.gen;

import java.awt.*;

public class Map {
    public static final int OBSTACLE = 1;
    public static final int FREE = 0;

    private final int[] array;

    public final int width;
    public final int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width * height];
    }

    public int get(Point point) {
        if (point.x < 0 || point.x >= width || point.y < 0 || point.y >= height) {
            return OBSTACLE;
        }
        return FREE; //TODO
    }
}
