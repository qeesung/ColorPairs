package io.github.qeesung.utils;

import com.intellij.openapi.components.ApplicationComponent;
import io.github.qeesung.data.ColorPairsConfigurationHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Created by qeesung on 2015/12/5.
 */
public class ColorPairsApplicationComponent implements ApplicationComponent {
    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {
        // save all configurations to config xml file
        ColorPairsConfigurationHandler.getMyInstance().saveConfig();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "ColorPairApplicationComponent";
    }
}
