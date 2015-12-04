package io.github.qeesung.data;

import groovy.transform.stc.PickAnyArgumentHint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/12/2.
 * use to save all configuration
 */
public class ColorPairsConfiguration {
    // store setting for every kind of pairs
    private Map<PairType, PairColorProperty> globalConfigMap = new HashMap<PairType, PairColorProperty>();
    // save the status that if the color pair is enabled
    private boolean colorPairsEnabled;

    public ColorPairsConfiguration(Map<PairType , PairColorProperty> map , boolean status)
    {
        this.globalConfigMap = map;
        colorPairsEnabled = status;
    }

    public ColorPairsConfiguration()
    {
        colorPairsEnabled = true;
    }

    public Map<PairType , PairColorProperty> getGlobalConfigMap()
    {
        return globalConfigMap;
    }

    public void setGlobalConfigMap(Map<PairType , PairColorProperty>  map)
    {
        this.globalConfigMap  = map;
    }

    public boolean isColorPairsEnabled()
    {
        return colorPairsEnabled;
    }

    public void setColorPairsEnabled(boolean state)
    {
        this.colorPairsEnabled = state;
    }

    public void clearConfiguration()
    {
        globalConfigMap.clear();
        colorPairsEnabled = true;
    }
}
