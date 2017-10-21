package com.krld.gen.models;

import com.krld.gen.GenWorld;

import java.util.ArrayList;
import java.util.List;

public class Bot {

    public List<Action> actions = new ArrayList<>(GenWorld.N_STEPS);

    public static Bot rootBot() {
        Bot bot = new Bot();
        for (int i = 0; i < GenWorld.N_STEPS; i++) {
            bot.actions.add(Action.STOP);
        }

        return bot;
    }

    public enum Action {
        GO_UP,
        GO_RIGHT,
        GO_DOWN,
        GO_LEFT,
        STOP
    }
}
