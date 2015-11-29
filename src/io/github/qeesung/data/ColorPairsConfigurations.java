package io.github.qeesung.data;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qeesung on 2015/11/29.
 * use to save all the color and shape configurations
 */
public class ColorPairsConfigurations {
    private static ColorPairsConfigurations Instance = null;

    // config file save and load path
    private static final String CONFIG_DIR =System.getenv("APPDATA")+"\\ColorPairs\\";
    private static final String CONFIG_XML_FILENAME = "ColorPairs.xml";

    // store setting for every kind of pairs
    private Map<PairType, PairColorProperty> globalConfigMap = new HashMap<PairType, PairColorProperty>();

    /**
     * get the only config instance
     * @return config instance
     */
    public static ColorPairsConfigurations getInstance()
    {
        if(Instance == null)
            Instance = new ColorPairsConfigurations();
        return Instance;
    }


    public ColorPairsConfigurations()
    {
        loadConfig();
    }

    /**
     * load the config from config xml file
     */
    public void loadConfig()
    {
        if(configFileIsExists()) // check if the config file is exists
            loadConfigFromXmlFile(globalConfigMap , CONFIG_DIR+CONFIG_XML_FILENAME);
        else // load from default setting then write it to config file
        {
            globalConfigMap = ColorPairsDefaultSetting.getDefaultSetting();
            writeConfigToXmlFile(globalConfigMap , CONFIG_DIR+CONFIG_XML_FILENAME);
        }
    }

    /**
     * sync current config to config file
     */
    public void saveConfig()
    {
        saveConfig(globalConfigMap);
    }

    /**
     * sync config map to config xml file
     * @param configMap configurations
     */
    public void saveConfig(Map<PairType , PairColorProperty> configMap)
    {
        if(configMap == null)
            return;
        globalConfigMap = configMap;
        // write the config to xml file
        writeConfigToXmlFile(configMap , CONFIG_DIR+CONFIG_XML_FILENAME);
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
     * @param configMap configurations
     */
    private void writeConfigToXmlFile(Map<PairType , PairColorProperty> configMap , String configFile)
    {
        if(configMap == null)
            return;
        if(!configFileIsExists())
        {
            File file = new File(CONFIG_DIR);
            file.mkdirs();
        }
        ColorPairsConfigFileHandler.writeConfigToXmlFile(configMap , configFile);
    }

    /**
     * load configurations from config xml file
     * @param configMap config map to be set
     */
    private void loadConfigFromXmlFile(Map<PairType , PairColorProperty> configMap , String configFile)
    {
        if(configMap == null)
            return;
        ColorPairsConfigFileHandler.readConfigFromXmlFile(configMap , configFile);
    }
}
