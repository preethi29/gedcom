package com.company.gedcom;

import com.company.gedcom.models.Node;
import com.company.gedcom.parsers.GedcomFileParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException, ParserConfigurationException, URISyntaxException {
        List<Node> nodes = new GedcomFileParser().parseGedcomFile("sample.txt");
    }

}
