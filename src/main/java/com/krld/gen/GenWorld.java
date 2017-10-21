package com.krld.gen;

import com.krld.gen.models.Bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GenWorld {
    public static final int N_STEPS = 1000;

    Point targetPoint = new Point(11, 10);

    public void run() {
        List<Bot> generation = getInitialBots();

        testGeneration(generation);
        //TODO test generation
        //TODO pick best
        //TODO mutate
        //TODO repeat


    }

    private void testGeneration(List<Bot> generation) {
        for (Bot bot : generation) {
            testBot(bot);
        }
    }

    private void testBot(Bot bot) {


        Point currentPoint = new Point(10, 10);
        List<Bot.Action> actions = bot.actions;
        for (int i = 0; i < actions.size(); i++) {
            Bot.Action action = actions.get(i);
            switch (action) {

            }
        }
    }

    private ArrayList<Bot> getInitialBots() {
        ArrayList<Bot> bots = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            bots.add(Bot.rootBot());
        }
        return bots;
    }
}
