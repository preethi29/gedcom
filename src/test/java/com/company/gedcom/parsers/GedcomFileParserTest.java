package com.company.gedcom.parsers;

import com.company.gedcom.models.Node;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class GedcomFileParserTest {

    private GedcomFileParser spyGedcomFileParser = spy(new GedcomFileParser());

    @Test
    public void shouldCreateListOfNodesFromFileInput() throws Exception {
        String inputLine1 = "0 @I001@ INDI";
        String inputLine2 = "1 @I002@ INDI";
        doReturn(Stream.of(inputLine1, inputLine2)).when(spyGedcomFileParser).readLinesFromFile("sample.txt");
        Node node1 = new Node(0, "@I001@", "indi", null);
        Node node2 = new Node(1, "@I002@", "indi", null);
        doReturn(node1).when(spyGedcomFileParser).createNode(inputLine1);
        doReturn(node2).when(spyGedcomFileParser).createNode(inputLine2);

        List<Node> nodes = spyGedcomFileParser.parseGedcomFile("sample.txt");

        assertThat(nodes).containsOnly(node1, node2);
    }

    @Test
    public void shouldCreateNodeWithTypeAsValueWhenIdIsProvided() throws Exception {

        Node node = spyGedcomFileParser.createNode("0 @I001@ INDI");

        assertThat(node.getLevel()).isEqualTo(0);
        assertThat(node.getType()).isEqualTo("indi");
        assertThat(node.getId()).isEqualTo("@I001@");
        assertThat(node.getValue()).isNull();
    }

    @Test
    public void shouldCreateNodeWithValueWhenTagIsProvided() throws Exception {

        Node node = spyGedcomFileParser.createNode("1 NAME Preethi");

        assertThat(node.getLevel()).isEqualTo(1);
        assertThat(node.getType()).isEqualTo("name");
        assertThat(node.getId()).isNull();
        assertThat(node.getValue()).isEqualTo("Preethi");
    }

    @Test
    public void shouldCreateNodeWithNoValueWhenValueIsNotProvided() throws Exception {

        Node node = spyGedcomFileParser.createNode("2 CHAN");

        assertThat(node.getLevel()).isEqualTo(2);
        assertThat(node.getType()).isEqualTo("chan");
        assertThat(node.getId()).isNull();
        assertThat(node.getValue()).isNull();
    }
}