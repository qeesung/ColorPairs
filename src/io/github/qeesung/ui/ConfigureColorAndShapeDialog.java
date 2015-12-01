package io.github.qeesung.ui;

import com.intellij.ui.ColorChooser;
import io.github.qeesung.data.*;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigureColorAndShapeDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JLabel roundBracketColorLabel;
    private JComboBox roundBracketComboBox;
    private JLabel squareBracketColorLabel;
    private JComboBox squareBracketComboBox;
    private JLabel curlBracketColorLabel;
    private JComboBox curlBracketComboBox;
    private JLabel doubleQuoteColorLabel;
    private JComboBox doubleQuoteComboBox;
    private JLabel singleQuoteColorLabel;
    private JComboBox singleQuoteComboBox;
    private JLabel angleBracketColorLabel;
    private JComboBox angleBracketComboBox;
    private JLabel missPairColorLabel;
    private JComboBox missPairComboBox;
    private JButton resetButton;

    private Map<PairType , Pair<JLabel , JComboBox>> colorShapeMap = new HashMap<>();

    public ConfigureColorAndShapeDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetConfiguration();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // bind all pairs with it color label and shape combobox
        {
            colorShapeMap.put(PairType.ROUND_BRACKET , new Pair(roundBracketColorLabel, roundBracketComboBox));
            colorShapeMap.put(PairType.SQUARE_BRACKET , new Pair(squareBracketColorLabel, squareBracketComboBox));
            colorShapeMap.put(PairType.CURL_BRACKET , new Pair(curlBracketColorLabel, curlBracketComboBox));
            colorShapeMap.put(PairType.DOUBLE_QUOTE , new Pair(doubleQuoteColorLabel, doubleQuoteComboBox));
            colorShapeMap.put(PairType.SINGLE_QUOTE , new Pair(singleQuoteColorLabel, singleQuoteComboBox));
            colorShapeMap.put(PairType.ANGLE_BRACKET , new Pair(angleBracketColorLabel, angleBracketComboBox));
            colorShapeMap.put(PairType.MISS_PAIR , new Pair(missPairColorLabel, missPairComboBox));
        }

        // add mouse listener for color selector
        {
            roundBracketColorLabel.addMouseListener(new ChangeColorListener());
            squareBracketColorLabel.addMouseListener(new ChangeColorListener());
            curlBracketColorLabel.addMouseListener(new ChangeColorListener());
            doubleQuoteColorLabel.addMouseListener(new ChangeColorListener());
            singleQuoteColorLabel.addMouseListener(new ChangeColorListener());
            angleBracketColorLabel.addMouseListener(new ChangeColorListener());
            missPairColorLabel.addMouseListener(new ChangeColorListener());
        }

        refreshConfiguration();
    }

    private void onOK() {
        // save all color and shape configurations to global configuration
        // and cofig file
        saveConfigurations();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * reset the configurations to default
     */
    private void resetConfiguration()
    {
        ColorPairsConfigurations.getInstance().saveConfig(ColorPairsDefaultSetting.getDefaultSetting());
        refreshConfiguration();
    }

    /**
     * reload the configurations and show on the dialog
     */
    private void refreshConfiguration()
    {
        Map<PairType , PairColorProperty> configMap = ColorPairsConfigurations.getInstance().getGlobalConfigMap();
        for(PairType type : configMap.keySet())
        {
            PairColorProperty property = configMap.get(type);
            Pair<JLabel , JComboBox> colorShapePair = colorShapeMap.get(type);
            if(colorShapePair == null)
                continue;
            colorShapePair.getKey().setBackground(property.getPairColor());
            colorShapePair.getValue().setSelectedItem(property.getPairColorShape().toString().toLowerCase());
        }
        this.repaint();
    }

    /**
     * save current configurations
     */
    private void saveConfigurations()
    {
        Map<PairType , PairColorProperty> toBeSaveConfiguration = new HashMap<>();
        for (PairType type: colorShapeMap.keySet())
        {
            Pair<JLabel , JComboBox> colorShapePair = colorShapeMap.get(type);

            Color color = colorShapePair.getKey().getBackground();
            PairColorShape shape = PairColorShape.valueOf(colorShapePair.getValue().getSelectedItem().toString().toUpperCase());
            PairColorProperty property = new PairColorProperty(color , shape);
            toBeSaveConfiguration.put(type , property);
        }
        ColorPairsConfigurations.getInstance().saveConfig(toBeSaveConfiguration);
    }

    /**
     * use to change the pair color
     */
    class ChangeColorListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e)
        {
            Object object = e.getSource();
            if(object instanceof JLabel)
            {
                JLabel targetLabel= (JLabel)object;
                if(targetLabel == null)
                    return;
                // open the color chooser
                Color c = ColorChooser.chooseColor(ConfigureColorAndShapeDialog.this,"Choose Pair Color",targetLabel.getBackground());
                if(c != null)
                    targetLabel.setBackground(c);
            }
        }
    }

    public static void main(String[] args) {
        ConfigureColorAndShapeDialog dialog = new ConfigureColorAndShapeDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
