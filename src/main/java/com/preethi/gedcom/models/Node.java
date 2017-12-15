package com.preethi.gedcom.models;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Integer level;
    private String id;
    private String type;
    private String value;
    private List<Node> children = new ArrayList<>();
    private Node parentNode;

    public Node(Integer level, String type) {
        this.level = level;
        this.type = type;
    }

    public Node(Integer level, String id, String type, String value) {
        this.level = level;
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Integer getLevel() {
        return level;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void addChild(Node node) {
        this.children.add(node);
        node.setParentNode(this);
    }

    public void addSibling(Node node) {
        this.parentNode.addChild(node);
    }

    private void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
}
