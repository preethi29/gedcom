package com.preethi.gedcom.parsers;

import com.preethi.gedcom.models.Node;

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
        return inputLines.filter(line -> !line.matches("\\s+")).map(this::createNode).collect(toList());
    }

    Node createNode(String line) {
        String[] parts = line.split("\\s+", 3);
        Integer level = Integer.valueOf(parts[0]);
        String id, type, value;
        if (parts[1].length() <= MAX_TAG_LENGTH) {
            type = parts[1].toLowerCase();
            value = parts.length > 2 ? parts[2] : null;
            return new Node(level, null, type, value);
        }
        id = parts[1];
        type = parts.length > 2 ? parts[2].toLowerCase(): null;
        return new Node(level, id, type, null);
    }

    Stream<String> readLinesFromFile(String filePath) throws IOException, URISyntaxException {
        return Files.lines(Paths.get(filePath));
    }
}
