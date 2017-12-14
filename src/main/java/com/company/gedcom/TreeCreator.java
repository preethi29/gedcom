package com.company.gedcom;

import com.company.gedcom.models.Node;

import java.util.List;

public class TreeCreator {

    private static final String GEDCOM_TAG = "gedcom";

    public Node formTree(List<Node> nodes) {
        Node rootNode = new Node(-1, GEDCOM_TAG);
        createChild(rootNode, 0, nodes);
        return rootNode;
    }

    void createChild(Node currentNode, int nextIndex, List<Node> nodes) {
        if (nextIndex == nodes.size()) {
            return;
        }
        Node nextNode = nodes.get(nextIndex);
        if (currentNode.getLevel() < nextNode.getLevel()) {
            currentNode.addChild(nextNode);
            createChild(nextNode, nextIndex + 1, nodes);
        } else if (currentNode.getLevel().equals(nextNode.getLevel())) {
            currentNode.addSibling(nextNode);
            createChild(nextNode, nextIndex + 1, nodes);
        } else {
            createChild(currentNode.getParentNode(), nextIndex, nodes);
        }
    }
}
