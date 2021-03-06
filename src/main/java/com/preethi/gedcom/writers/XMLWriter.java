package com.preethi.gedcom.writers;

import com.preethi.gedcom.models.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLWriter implements OutputWriter{

    public void write(Node rootNode) {
        Document dom = createEmptyDocument();
        Element rootElement = dom.createElement(rootNode.getType());
        rootNode.getChildren().forEach(child -> createChildElement(dom, rootElement, child));
        dom.appendChild(rootElement);
        try {
            Transformer tr = buildTransformer();
            tr.transform(new DOMSource(dom),
                    new StreamResult(new FileOutputStream("gedcom.xml")));

        } catch (TransformerException | IOException te) {
            System.out.println(te.getMessage());
        }
    }

    Document createEmptyDocument() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Could not build xml document", e);
        }
        return db.newDocument();
    }

    Transformer buildTransformer() throws TransformerConfigurationException {
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return tr;
    }

    void createChildElement(Document dom, Element rootEle, Node node) {
        Element element = dom.createElement(node.getType());
        if (node.getId() != null) {
            element.setAttribute("id", node.getId());
        }
        if (node.getValue() != null) {
            if (node.getChildren().isEmpty()) {
                element.appendChild(dom.createTextNode(node.getValue()));
            } else {
                element.setAttribute("value", node.getValue());
            }
        }
        node.getChildren().forEach(child -> createChildElement(dom, element, child));
        rootEle.appendChild(element);
    }
}
