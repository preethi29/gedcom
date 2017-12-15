package com.preethi.gedcom;


import com.preethi.gedcom.models.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class TreeCreatorTest {

    private TreeCreator treeCreator = new TreeCreator();

    @Test
    public void shouldGiveRootNodeAsGedcom() throws Exception {
        Node node = treeCreator.formTree(Collections.emptyList());

        assertThat(node.getType()).isEqualTo("gedcom");
    }

    @Test
    public void shouldCreateChildWhenLevelIsGreaterThanCurrentNodeLevel() throws Exception {
        Node currentNode = new Node(1, "Name");
        Node nextNode = new Node(2, "first");

        treeCreator.createChild(currentNode, 1, asList(currentNode, nextNode));

        assertThat(currentNode.getChildren()).contains(nextNode);
    }

    @Test
    public void shouldKeepAddingChildrenUntilLevelIsGreaterThanCurrentNodeLevel() throws Exception {
        Node rootNode = new Node(1, "Name");
        Node node1 = new Node(2, "first");
        Node node2 = new Node(3, "last");

        treeCreator.createChild(rootNode, 1, asList(rootNode, node1, node2));

        assertThat(rootNode.getChildren()).containsOnly(node1);
        assertThat(node1.getChildren()).containsOnly(node2);
        assertThat(node2.getChildren()).isEmpty();
    }

    @Test
    public void shouldAddNodeAsSiblingWhenLevelIsEqualToCurrentNodeLevel() throws Exception {
        Node rootNode = new Node(1, "Name");
        Node currentNode = new Node(2, "first");
        rootNode.addChild(currentNode);
        Node nextNode = new Node(2, "last");

        treeCreator.createChild(rootNode, 2, asList(rootNode, currentNode, nextNode));

        assertThat(rootNode.getChildren()).containsOnly(currentNode, nextNode);
    }

    @Test
    public void shouldAddPreviouslyEncounteredNodeWithLesserLevelAsParentWhenLevelIsGreaterThanCurrentNodeLevel() throws Exception {
        Node rootNode = new Node(0, "INDI");
        Node node1 = new Node(1, "Name");
        rootNode.addChild(node1);
        Node node2 = new Node(2, "first");
        node1.addChild(node2);
        Node nextNode = new Node(1, "sex");

        treeCreator.createChild(rootNode, 3, asList(rootNode, node1, node2, nextNode));

        assertThat(rootNode.getChildren()).containsOnly(node1, nextNode);
        assertThat(node1.getChildren()).containsOnly(node2);
        assertThat(node2.getChildren()).isEmpty();

    }
}