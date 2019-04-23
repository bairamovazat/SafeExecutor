package ru.ivmiit.web.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;

public class PolygonXmlParser {

    public static String parseXml(String filePath) throws DocumentException {
        File inputFile = new File(filePath);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);

        Element rootElement = document.getRootElement();
        return rootElement.getName();
    }
}
