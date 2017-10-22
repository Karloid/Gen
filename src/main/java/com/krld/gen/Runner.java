package com.krld.gen;

public class Runner {
    public static void main(String[] args) {
        GenWorld w = new GenWorld();

        //TODO visualization
        new MyFrame(w);
        w.run();
    }
}
