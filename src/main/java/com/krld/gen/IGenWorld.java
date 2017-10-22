package com.krld.gen;

import java.awt.*;

public interface IGenWorld {
    void setUiDelegate(UIDelegate delegate);

    WorldMap getMap();

    Point getTargetPoint();

    Point getStartPoint();
}
