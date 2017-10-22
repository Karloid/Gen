package com.krld.gen;

import com.krld.gen.models.Bot;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TODO look at fitness calculation
public class GenWorld implements IGenWorld{
    public static final int N_STEPS = 3000;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    public static final int PICK_TOP_MUTATIONS = 10;
    public static final int GENERATION_SIZE = 1000;

    Point targetPoint;
    private Point startPoint;
    private WorldMap worldMap;
    private UIDelegate uiDelegate;
    private int mutationCount = 40;

    public GenWorld() {
        generateMap();
    }

    public void run() {

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
                        generation.add(Bot.child(bestBot, this));
                    }
                }
                uiDelegate.onBestBotsCalculated(bestBots);
                
                //hac for painting
                for (Bot bestBot : bestBots) {
                    testBot(bestBot);
                }
            }
            uiDelegate.onEndTest(i, generation);
            uiDelegate.onStartTest(i, generation);
            testGeneration(generation);

        }
    }

    private void generateMap() {
        startPoint = new Point(10, 10);
        targetPoint = new Point(WIDTH - 66, HEIGHT - 66);

        worldMap = new WorldMap(WIDTH, HEIGHT);
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
                    newPos.x--;
                    break;
                case STOP:
                    break;
            }

            int m = worldMap.get(newPos);

            if (m == WorldMap.FREE) {
                currentPoint = newPos;
                uiDelegate.onNewPoint(bot, newPos);
                if (currentPoint.equals(targetPoint)) {
                    bot.fitness = i;
                    break;
                }
            }
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

    public void setUiDelegate(UIDelegate delegate) {
        this.uiDelegate = delegate;
    }

    @Override
    public WorldMap getMap() {
        return worldMap;
    }

    @Override
    public Point getTargetPoint() {
        return targetPoint;
    }

    @Override
    public Point getStartPoint() {
        return startPoint;
    }

    @Override
    public void addObstacle(Point point) {
        worldMap.addObstacleTriple(point);
    }

    public int getMutationCount() {
        return mutationCount;
    }
}
