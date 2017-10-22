package com.krld.gen;

public class FlatArray<T> {
    private Object[] array = new Object[0];
    private int width;
    private int height;

    public void clear() {
        setSize(width, height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        array = new Object[width * height];
    }

    public void set(int x, int y, T entity) {
        array[getIndex(x, y)] = entity;
    }

    private int getIndex(int x, int y) {
        return x * height + y;
    }

    public T get(int x, int y) {
        //noinspection unchecked
        return (T) array[getIndex(x, y)];
    }
}
