package com.company.gedcom;

import com.company.gedcom.models.Node;
import com.company.gedcom.parsers.GedcomFileParser;
import com.company.gedcom.writers.XMLWriter;

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
        List<Node> nodes = new GedcomFileParser().parseGedcomFile(path);
        Node root = new TreeCreator().formTree(nodes);
        new XMLWriter().write(root);
    }

}
