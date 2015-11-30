package io.github.qeesung.utils;

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
     * @param configMap all configurations
     * @param configFile config xml file path
     */
    public static void writeConfigToXmlFile(Map<PairType, PairColorProperty> configMap , String configFile)
    {
        if(configFile == null || configFile == null)
            return;

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
            colorElement.appendChild(doc.createTextNode(property.getPairColor().getRGB()+""));
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


    public static void readConfigFromXmlFile(Map<PairType , PairColorProperty> configMap , String configFile)
    {
        if(configMap == null || configFile == null)
            return;
        configMap.clear(); // remove all old configurations

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

        NodeList nList = doc.getElementsByTagName(ColorPairsConfigFileTagName.PAIR_TAG);
        for (int i = 0; i < nList.getLength() ; i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;
                //  read all configurations from xml file
                String typeValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.TYPE_TAG).item(0).getTextContent();
                String colorValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.COLOR_TAG).item(0).getTextContent();
                int colorREGValue = Integer.parseInt(colorValue);
                String shapeValue = eElement.getElementsByTagName(ColorPairsConfigFileTagName.SHAPE_TAG).item(0).getTextContent();

                configMap.put(PairType.valueOf(typeValue) ,
                        new PairColorProperty(new Color(colorREGValue) , PairColorShape.valueOf(shapeValue)));
            }
        }

    }
}
