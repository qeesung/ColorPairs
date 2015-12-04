package io.github.qeesung.utils;

import io.github.qeesung.data.ColorPairsConfiguration;
import io.github.qeesung.data.PairColorProperty;
import io.github.qeesung.data.PairColorShape;
import io.github.qeesung.data.PairType;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by qeesung on 2015/11/29.
 */
public class ColorPairsConfigFileHandler {

    /**
     * write all config to a xml file
     * @param configuration all configurations
     * @param configFile config xml file path
     */
    public static void writeConfigToXmlFile(ColorPairsConfiguration configuration, String configFile)
    {
        if(configuration == null || configFile == null)
            return;

        Map<PairType , PairColorProperty> configMap = configuration.getGlobalConfigMap();
        boolean enabled = configuration.isColorPairsEnabled();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        // root elements
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(ColorPairsConfigFileTagName.ROOT_TAG);
        doc.appendChild(rootElement);

        Attr enableAttr = doc.createAttribute(ColorPairsConfigFileTagName.ENABLE_ATTR);
        enableAttr.setValue(enabled+"");
        rootElement.setAttributeNode(enableAttr);

        // write all pairs
        for (PairType type : configMap.keySet() )
        {
            PairColorProperty property = configMap.get(type);

            // pair elements
            Text lineBreak1 = doc.createTextNode("\n\t");
            rootElement.appendChild(lineBreak1);
            Element pairElement = doc.createElement(ColorPairsConfigFileTagName.PAIR_TAG);
            rootElement.appendChild(pairElement);

            // add pair all child tag here


            //type tag
            Text lineBreak2 = doc.createTextNode("\n\t\t");
            pairElement.appendChild(lineBreak2);
            Element typeElement = doc.createElement(ColorPairsConfigFileTagName.TYPE_TAG);
            typeElement.appendChild(doc.createTextNode(type.toString()));
            pairElement.appendChild(typeElement);


            //color tag
            Text lineBreak3 = doc.createTextNode("\n\t\t");
            pairElement.appendChild(lineBreak3);
            Element colorElement = doc.createElement(ColorPairsConfigFileTagName.COLOR_TAG);
            colorElement.appendChild(doc.createTextNode(convertColorToHex(property.getPairColor())));
            pairElement.appendChild(colorElement);

            //shape tag
            Text lineBreak4 = doc.createTextNode("\n\t\t");
            pairElement.appendChild(lineBreak4);
            Element shapeElement = doc.createElement(ColorPairsConfigFileTagName.SHAPE_TAG);
            shapeElement.appendChild(doc.createTextNode(property.getPairColorShape().toString()));
            pairElement.appendChild(shapeElement);

            // add pair tag line break
            Text lineBreak5 = doc.createTextNode("\n\t");
            pairElement.appendChild(lineBreak5);
        }
        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(configFile));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


    public static void readConfigFromXmlFile(ColorPairsConfiguration configuration, String configFile)
    {
        if(configuration == null || configFile == null)
            return;
        configuration.clearConfiguration(); // remove all old configurations

        Map<PairType , PairColorProperty> configMap = configuration.getGlobalConfigMap();

        File fXmlFile = new File(configFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        NodeList pairsNodeList = doc.getElementsByTagName(ColorPairsConfigFileTagName.ROOT_TAG);
        if(pairsNodeList.getLength() == 0)
            return;
        // get the enable attr
        {
            Node node = pairsNodeList.item(0);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element pairNode = (Element)node;
                Attr attr = pairNode.getAttributeNode(ColorPairsConfigFileTagName.ENABLE_ATTR);
                configuration.setColorPairsEnabled(attr.getValue().equals("true") ? true: false );
            }
        }

        NodeList nList = doc.getElementsByTagName(ColorPairsConfigFileTagName.PAIR_TAG);
        for (int i = 0; i < nList.getLength() ; i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                //  read all configurations from xml file
                String typeValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.TYPE_TAG).item(0).getTextContent();
                String colorValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.COLOR_TAG).item(0).getTextContent();
                Color color = convertHexToColor(colorValue);
                String shapeValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.SHAPE_TAG).item(0).getTextContent();

                configMap.put(PairType.valueOf(typeValue) ,
                        new PairColorProperty(color , PairColorShape.valueOf(shapeValue)));
            }
        }

    }

    private static String convertColorToHex(Color color)
    {
        return "#"+Integer.toHexString(color.getRGB()).substring(2);
    }

    private static Color convertHexToColor(String hex)
    {
        int rgb = Integer.parseInt(hex.substring(1), 16 );
        return new Color(rgb);
    }
}
