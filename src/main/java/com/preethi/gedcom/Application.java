package com.preethi.gedcom;

import com.preethi.gedcom.models.Node;
import com.preethi.gedcom.parsers.GedcomFileParser;
import com.preethi.gedcom.writers.OutputWriter;
import com.preethi.gedcom.writers.XMLWriter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException, ParserConfigurationException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please specify the path of gedcom file:");
        String path = scanner.next();
        OutputWriter outputWriter = new XMLWriter();

        List<Node> nodes = new GedcomFileParser().parseGedcomFile(path);
        Node rootNode = new TreeCreator().formTree(nodes);
        outputWriter.write(rootNode);
    }

}
