package com.krld.gen;

import com.krld.gen.models.Bot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements UIDelegate {

    public static final int INFO_PANEL_HEIGHT = 46;
    public static final int UPDATE_DELTA = 64;
    private final int width;
    private final int height;
    private final DrawPane drawPane;
    private long lastUpdate;
    private int generation;
    private List<Bot> bots = new ArrayList<>();
    private boolean waitingWorld;
    private IGenWorld world;

    private FlatArray<Bot> pathBuffer = new FlatArray<>();
    private List<Bot> bestBots;

    public MyFrame(IGenWorld w) {
        world = w;

        drawPane = new DrawPane();
        setContentPane(drawPane);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        width = w.getMap().width;
        height = w.getMap().height;
        pathBuffer.setSize(width, height);

        setSize(width, height + INFO_PANEL_HEIGHT);

        setVisible(true);


        w.setUiDelegate(this);
    }

    @Override
    public void onStartTest(int generation, List<Bot> bots) {

        if (System.currentTimeMillis() - lastUpdate > UPDATE_DELTA) {
            this.generation = generation;
            this.bots = bots;

            waitingWorld = true;
            lastUpdate = System.currentTimeMillis();

            pathBuffer.clear();
        }
    }

    @Override
    public void onEndTest(int i, List<Bot> generation) {
        if (waitingWorld) {
            drawPane.repaint();
            waitingWorld = false;
        }
    }

    @Override
    public void onNewPoint(Bot bot, Point pos) {
        if (!waitingWorld) {
            return;
        }

        pathBuffer.set(pos.x, pos.y, bot);

        //TODO save points
    }

    @Override
    public void onBestBotsCalculated(List<Bot> bestBots) {
        if (!waitingWorld) {
            return;
        }
        this.bestBots = bestBots;
    }

    private class DrawPane extends JPanel {
        int yOffset = INFO_PANEL_HEIGHT;

        public DrawPane() {

            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point point = e.getPoint();
                    point.y -= yOffset;
                    world.addObstacle(point);
                }
            };

            addMouseListener(ma);
            addMouseMotionListener(ma);
        }

        @Override
        protected void paintComponent(Graphics g) {


            //info
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, width, yOffset);
            g.setColor(Color.black);

            g.drawString("G#: " + generation + ", bot count: " + bots.size(), 4, 20);

            //map
            g.setColor(Color.gray);
            g.fillRect(0, yOffset, width, height);

            drawMap(g);


            //target
            Point target = world.getTargetPoint();
            Point start = world.getStartPoint();


            drawPaths(g);

            // g.setColor(Color.blue);
            // g.drawLine(target.x, target.y + yOffset, start.x, start.y + yOffset);
            drawPoint(g, target, Color.red);
            drawPoint(g, start, Color.green);

        }

        private void drawMap(Graphics g) {
            WorldMap map = world.getMap();
            g.setColor(Color.darkGray);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (map.get(x, y) == WorldMap.OBSTACLE) {
                        g.fillRect(x, y + yOffset, 1, 1);
                    }
                }
            }
        }

        private void drawPaths(Graphics g) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Bot bot = pathBuffer.get(x, y);

                    if (bot != null) {
                        drawPoint(g, x, y, getColor(bot));
                    }
                }
            }
        }

        private Color getColor(Bot bot) {
            if (bestBots.contains(bot)) {
                return Color.red;
            }
            return Color.black;
        }

        private void drawPoint(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.fillRect(x, y + yOffset, 1, 1);
        }

        private void drawPoint(Graphics g, Point target, Color red) {
            g.setColor(red);
            g.fillRect(target.x, yOffset + target.y, 1, 1);
        }
    }
}
