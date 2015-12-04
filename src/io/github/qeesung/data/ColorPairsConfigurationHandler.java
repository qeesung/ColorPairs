package io.github.qeesung.data;


import io.github.qeesung.utils.ColorPairsConfigFileHandler;

import java.io.File;
import java.util.Map;

/**
 * Created by qeesung on 2015/11/29.
 * use to save all the color and shape configuration
 */
public class ColorPairsConfigurationHandler {
    private static ColorPairsConfigurationHandler myInstance;

    // config file save and load path
    private static final String CONFIG_DIR =System.getenv("APPDATA")+File.separator+"ColorPairs"+File.separator;
    private static final String CONFIG_XML_FILENAME = "ColorPairs.xml";

    // configuration
    private ColorPairsConfiguration configuration ;


    /**
     * get the only config instance
     * @return config instance
     */
    public static synchronized ColorPairsConfigurationHandler getMyInstance()
    {
        if(myInstance == null)
            myInstance = new ColorPairsConfigurationHandler();
        return myInstance;
    }


    public ColorPairsConfigurationHandler() {
        configuration = new ColorPairsConfiguration();
        loadConfig();
    }

    public ColorPairsConfiguration getConfiguration()
    {
        return this.configuration;
    }

    public boolean isColorPairsEnabled()
    {
        return this.configuration.isColorPairsEnabled();
    }

    public void setColorPairsEnabled(boolean state)
    {
        this.configuration.setColorPairsEnabled(state);
        saveConfig();
    }

    /**
     * load the config from config xml file
     */
    public void loadConfig()
    {
        if(configFileIsExists()) // check if the config file is exists
            loadConfigFromXmlFile( configuration, CONFIG_DIR+CONFIG_XML_FILENAME);
        else // load from default setting then write it to config file
        {
            configuration = ColorPairsDefaultConfiguration.getDefaultConfiguration();
            writeConfigToXmlFile(configuration , CONFIG_DIR+CONFIG_XML_FILENAME);
        }
    }

    /**
     * sync current config to config file
     */
    public void saveConfig()
    {
        saveConfig(configuration);
    }

    /**
     * sync config map to config xml file
     * @param _configuration configuration
     */
    public void saveConfig(ColorPairsConfiguration _configuration)
    {
        if(_configuration == null)
            return;
         configuration = _configuration;
        // write the config to xml file
        writeConfigToXmlFile(configuration , CONFIG_DIR+CONFIG_XML_FILENAME);
    }

    /**
     * check if the config xml file is exists
     * @return true is exists , false is not exists
     */
    private boolean configFileIsExists()
    {
        File configFile = new File(CONFIG_DIR+CONFIG_XML_FILENAME);

        if(configFile.exists() && !configFile.isDirectory()) // config file is exists
            return true;
        else // config file is not exists
            return false;
    }


    /**
     * write config to config xml file
     * @param configuration configuration
     */
    private void writeConfigToXmlFile(ColorPairsConfiguration configuration , String configFile)
    {
        if(configuration == null)
            return;
        if(!configFileIsExists())
        {
            File file = new File(CONFIG_DIR);
            file.mkdirs();
        }
        ColorPairsConfigFileHandler.writeConfigToXmlFile(configuration, configFile);
    }

    /**
     * load configuration from config xml file
     * @param  configuration to be set
     */
    private void loadConfigFromXmlFile(ColorPairsConfiguration configuration , String configFile)
    {
        if(configFile == null)
            return;
        ColorPairsConfigFileHandler.readConfigFromXmlFile(configuration , configFile);
    }
}
