package io.github.qeesung.data;

import java.awt.*;

/**
 * Created by qeesung on 2015/11/29.
 */
public class PairColorProperty {
    private Color pairColor  = new Color(255,255,255);
    private PairColorShape pairColorShape = PairColorShape.SOLID;

    public PairColorProperty(Color color , PairColorShape shape)
    {
        this.pairColor = color;
        this.pairColorShape = shape;
    }

    public Color getPairColor()
    {
        return pairColor;
    }

    public PairColorShape getPairColorShape()
    {
        return pairColorShape;
    }
}
