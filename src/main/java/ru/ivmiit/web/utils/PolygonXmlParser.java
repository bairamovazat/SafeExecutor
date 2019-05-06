package ru.ivmiit.web.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import ru.ivmiit.web.utils.model.PolygonProblem;

import java.io.File;

public class PolygonXmlParser {

    public static void main(String[] args) throws DocumentException {
        System.out.println(PolygonXmlParser.parseXml("/home/ejudge/Загрузки/problem.xml"));
    }

    public static PolygonProblem parseXml(String filePath) throws DocumentException {
        File inputFile = new File(filePath);
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputFile);

        Element rootElement = document.getRootElement();

        PolygonProblem polygonProblem = new PolygonProblem();

        polygonProblem.setProblemFileName(inputFile.getName());
        polygonProblem.setProblemDir(inputFile.getParent());

        rootElement.element("names").elements("name").forEach(element -> {
            polygonProblem.getNames().put(
                    element.attribute("language").getValue(),
                    element.attribute("value").getValue()
            );
        });

        Element testset = rootElement.element("judging").element("testset");

        polygonProblem.setTimeLimit(Integer.parseInt(testset.element("time-limit").getStringValue()));
        polygonProblem.setMemoryLimit(Integer.parseInt(testset.element("memory-limit").getStringValue()));
        polygonProblem.setTestCount(Integer.parseInt(testset.element("test-count").getStringValue()));
        polygonProblem.setInputTestPattern(testset.element("input-path-pattern").getStringValue());
        polygonProblem.setAnswerTestPattern(testset.element("answer-path-pattern").getStringValue());

        Element checkerSource = rootElement.element("assets").element("checker").element("source");
        polygonProblem.setCheckerSource(checkerSource.attribute("path").getValue());
        polygonProblem.setCheckerType(checkerSource.attribute("type").getValue());

        rootElement.element("documents").elements("document").forEach(element -> {
            polygonProblem.getDocuments().add(
                    element.attribute("path").getValue()
            );
        });


        return polygonProblem;
    }
}
