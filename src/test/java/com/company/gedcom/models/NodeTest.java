package com.company.gedcom.models;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class NodeTest {

    @Test
    public void shouldAddChildAndSetParentNodeOfChild() throws Exception {
        Node rootNode = new Node(1, "name");
        Node childNode = new Node(2, "first");

        rootNode.addChild(childNode);

        assertThat(rootNode.getChildren()).containsOnly(childNode);
        assertThat(childNode.getParentNode()).isEqualTo(rootNode);
    }
}