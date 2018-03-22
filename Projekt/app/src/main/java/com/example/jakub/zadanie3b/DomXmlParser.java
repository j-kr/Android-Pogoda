package com.example.jakub.zadanie3b;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class DomXmlParser {

    public DomXmlParser() {
    }


    public Document getDocument(String url) {

        Document document = null;
        try {

            URL dataUrl = new URL(url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(new InputSource(dataUrl.openStream()));

        } catch (Exception e) {

            e.printStackTrace();
        }


        return document;
    }

    /*
     * Getting node value
     * @param Element node
     * @param key string
     * */
    public String getValue(Element element, String str) {
        NodeList nlList = element.getElementsByTagName(str).item(0)
                .getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }

    public String getAtributeNameValue(Element element, String str) {
        //Node nlList = element.getElementsByTagName(str).item(0);
        Node nValue = element.getElementsByTagName(str).item(0).getAttributes().getNamedItem("name");
        return nValue.getNodeValue();
    }

    public String getAtributeValueValue(Element element, String str) {
        //Node nlList = element.getElementsByTagName(str).item(0);
        Node nValue = element.getElementsByTagName(str).item(0).getAttributes().getNamedItem("value");
        return nValue.getNodeValue();
    }

}
