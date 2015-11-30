package io.github.qeesung.data;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/11/29.
 * save all the default settings for Color Pairs, used only can not find config file
 */
public class ColorPairsDefaultSetting {
    private static final Map<PairType , PairColorProperty> defaultSettingMap= new HashMap<>();

    // all the default data here
    static
    {
        defaultSettingMap.put(PairType.ROUND_BRACKET , new PairColorProperty(new Color(123, 104, 238) ,PairColorShape.SOLID));
        defaultSettingMap.put(PairType.SQUARE_BRACKET, new PairColorProperty(new Color(67, 110, 238) ,PairColorShape.SOLID));
        defaultSettingMap.put(PairType.CURL_BRACKET, new PairColorProperty(new Color(238, 180, 34) ,PairColorShape.SOLID));
        defaultSettingMap.put(PairType.DOUBLE_QUOTE , new PairColorProperty(new Color(118, 238, 0) ,PairColorShape.SOLID));
        defaultSettingMap.put(PairType.SINGLE_QUOTE , new PairColorProperty(new Color(11, 65, 26) ,PairColorShape.SOLID));
        defaultSettingMap.put(PairType.ANGLE_BRACKET , new PairColorProperty(new Color(205, 92, 92) ,PairColorShape.UNDERLINE));
        defaultSettingMap.put(PairType.MISS_PAIR , new PairColorProperty(new Color(238, 13, 48) ,PairColorShape.OUTLINE));
        defaultSettingMap.put(PairType.OTHER_PAIR, new PairColorProperty(new Color(33, 232, 238) ,PairColorShape.OUTLINE));
    }

    public static Map<PairType , PairColorProperty> getDefaultSetting()
    {
        return defaultSettingMap;
    }
}
