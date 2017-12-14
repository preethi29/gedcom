package com.company.gedcom.models;

public class Node {
    private Integer level;
    private String id;
    private String type;
    private String value;

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

}
