package com.krld.gen;

import com.krld.gen.models.Bot;

import java.awt.*;
import java.util.List;

public interface UIDelegate {
    void onStartTest(int generation, List<Bot> bots);

    void onEndTest(int i, List<Bot> generation);

    void onNewPoint(Bot bot, Point pos);

    void onBestBotsCalculated(List<Bot> bestBots);
}
