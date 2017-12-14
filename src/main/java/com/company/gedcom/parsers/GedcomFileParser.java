package com.company.gedcom.parsers;

import com.company.gedcom.models.Node;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class GedcomFileParser {

    private static final int MAX_TAG_LENGTH = 4;

    public List<Node> parseGedcomFile(String fileName) throws IOException, URISyntaxException {
        Stream<String> inputLines = readLinesFromFile(fileName);
        return inputLines.map(this::createNode).collect(toList());
    }

    Node createNode(String line) {
        String[] parts = line.split(" ", 3);
        Integer level = Integer.valueOf(parts[0]);
        String idOrType = parts[1];
        String valueOrType = parts.length > 2 ? parts[2] : null;
        if (idOrType.length() <= MAX_TAG_LENGTH) {
            return new Node(level, null, idOrType, valueOrType);
        }
        return new Node(level, idOrType, valueOrType, null);
    }

    Stream<String> readLinesFromFile(String fileName) throws IOException, URISyntaxException {
        return Files.lines(Paths.get(getClass().getClassLoader().getResource(fileName).toURI()));
    }
}
