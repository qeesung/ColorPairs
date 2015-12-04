package io.github.qeesung.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import io.github.qeesung.data.ColorPairsConfigurationHandler;

/**
 * Created by qeesung on 2015/11/26.
 * an action that can triggle the color pair
 */
public class ColorPairAction extends ToggleAction {
    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        return ColorPairsConfigurationHandler.getMyInstance().isColorPairsEnabled();
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        ColorPairsConfigurationHandler.getMyInstance().setColorPairsEnabled(b);
    }

}
