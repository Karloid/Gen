package com.krld.gen.models;

import com.krld.gen.GenWorld;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    public List<Action> actions = new ArrayList<>(GenWorld.N_STEPS);
    public double fitness;

    public static Bot rootBot() {
        Bot bot = new Bot();
        for (int i = 0; i < GenWorld.N_STEPS; i++) {
            bot.actions.add(Action.STOP);
        }

        return bot;
    }

    public static Bot child(Bot parent) {
        Bot o = new Bot();
        o.actions = new ArrayList<>(parent.actions);
        int randomActionIndex = (int) (o.actions.size() * Math.random());
        o.actions.set(randomActionIndex, getRandomAction());
        return o;
    }

    private static Action getRandomAction() {
        return Action.values()[(int) (Action.values().length * Math.random())];
    }

    public enum Action {
        GO_UP,
        GO_RIGHT,
        GO_DOWN,
        GO_LEFT,
        STOP
    }
}
