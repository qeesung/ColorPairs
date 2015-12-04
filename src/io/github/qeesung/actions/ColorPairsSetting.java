package io.github.qeesung.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import io.github.qeesung.ui.ConfigureColorAndShapeDialog;

import java.awt.*;

/**
 * Created by qeesung on 2015/11/28.
 */
public class ColorPairsSetting extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // create configuration dialog
        ConfigureColorAndShapeDialog configureColorAndShapeDialog = new ConfigureColorAndShapeDialog();
        configureColorAndShapeDialog.setTitle("Configure the color and shape");
        configureColorAndShapeDialog.pack();

        // center the dialog
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - configureColorAndShapeDialog.getWidth()) / 2;
        final int y = (screenSize.height - configureColorAndShapeDialog.getHeight()) / 2;
        configureColorAndShapeDialog.setLocation(x, y);

        // show the dialog
        configureColorAndShapeDialog.setVisible(true);
    }
}
