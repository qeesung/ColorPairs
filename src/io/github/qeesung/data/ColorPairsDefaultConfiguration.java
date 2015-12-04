package io.github.qeesung.data;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/11/29.
 * save all the default settings for Color Pairs, used only can not find config file
 */
public class ColorPairsDefaultConfiguration {

    private static final ColorPairsConfiguration defaultConfigration = new ColorPairsConfiguration();

    // all the default data here
    static
    {
        defaultConfigration.getGlobalConfigMap().put(PairType.ROUND_BRACKET , new PairColorProperty(new Color(123, 104, 238) ,PairColorShape.SOLID));
        defaultConfigration.getGlobalConfigMap().put(PairType.SQUARE_BRACKET, new PairColorProperty(new Color(67, 110, 238) ,PairColorShape.SOLID));
        defaultConfigration.getGlobalConfigMap().put(PairType.CURL_BRACKET, new PairColorProperty(new Color(238, 180, 34) ,PairColorShape.SOLID));
        defaultConfigration.getGlobalConfigMap().put(PairType.DOUBLE_QUOTE , new PairColorProperty(new Color(118, 238, 0) ,PairColorShape.SOLID));
        defaultConfigration.getGlobalConfigMap().put(PairType.SINGLE_QUOTE , new PairColorProperty(new Color(11, 65, 26) ,PairColorShape.SOLID));
        defaultConfigration.getGlobalConfigMap().put(PairType.ANGLE_BRACKET , new PairColorProperty(new Color(205, 92, 92) ,PairColorShape.UNDERLINE));
        defaultConfigration.getGlobalConfigMap().put(PairType.MISS_PAIR , new PairColorProperty(new Color(238, 13, 48) ,PairColorShape.OUTLINE));
        defaultConfigration.getGlobalConfigMap().put(PairType.OTHER_PAIR, new PairColorProperty(new Color(33, 232, 238) ,PairColorShape.OUTLINE));

        defaultConfigration.setColorPairsEnabled(true);
    }

    public static ColorPairsConfiguration getDefaultConfiguration()
    {
        return defaultConfigration;
    }
}
