package com.krld.gen;

import com.krld.gen.models.Bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GenWorld {
    public static final int N_STEPS = 1000;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    public static final int PICK_TOP_MUTATIONS = 10;
    public static final int GENERATION_SIZE = 1000;

    Point targetPoint;
    private Point startPoint;
    private Map map;

    public void run() {
        generateMap();

        List<Bot> generation = null;

        int generationNumber = 5000;
        for (int i = 0; i < generationNumber; i++) {
            System.out.println("\n Generation #-" + i);
            if (generation == null) {
                generation = getInitialBots();
            } else {
                generation.sort((o1, o2) -> Double.compare(o1.fitness, o2.fitness));
                List<Bot> bestBots = new ArrayList<>(generation.subList(0, PICK_TOP_MUTATIONS));

                generation = new ArrayList<>();
                int childrenPerBot = GENERATION_SIZE / PICK_TOP_MUTATIONS;


                for (int j = 0; j < bestBots.size(); j++) {
                    Bot bestBot = bestBots.get(j);
                    System.out.println("j: " + j + " fitness: " + bestBot.fitness);

                    for (int i1 = 0; i1 < childrenPerBot; i1++) {
                        generation.add(Bot.child(bestBot));
                    }
                }
            }
            testGeneration(generation);
        }
        //TODO test generation
        //TODO pick best
        //TODO mutate
        //TODO repeat


    }

    private void generateMap() {
        startPoint = new Point(10, 10);
        targetPoint = new Point(WIDTH - 10, HEIGHT - 10);

        map = new Map(WIDTH, HEIGHT);
    }

    private void testGeneration(List<Bot> generation) {
        for (Bot bot : generation) {
            testBot(bot);
        }
        int x = 10;
    }

    private void testBot(Bot bot) {


        Point currentPoint = new Point(startPoint);
        List<Bot.Action> actions = bot.actions;
        int size = actions.size();
        bot.fitness = size;
        for (int i = 0; i < size; i++) {
            Bot.Action action = actions.get(i);
            Point newPos = new Point(currentPoint);
            switch (action) {
                case GO_UP:
                    newPos.y--;
                    break;
                case GO_RIGHT:
                    newPos.x++;
                    break;
                case GO_DOWN:
                    newPos.y++;
                    break;
                case GO_LEFT:
                    newPos.y--;
                    break;
                case STOP:
                    break;
            }

            int m = map.get(newPos);

            if (m == Map.FREE) {
                currentPoint = newPos;
            }

            if (currentPoint.equals(targetPoint)) {
                bot.fitness = i;
                break;
            }
            //TODO check valid
        }

        bot.fitness += getDist(currentPoint, targetPoint);
    }

    private double getDist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private ArrayList<Bot> getInitialBots() {
        ArrayList<Bot> bots = new ArrayList<>();
        for (int i = 0; i < GENERATION_SIZE; i++) {
            bots.add(Bot.rootBot());
        }
        return bots;
    }
}
