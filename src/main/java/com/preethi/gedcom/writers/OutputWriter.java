package com.preethi.gedcom.writers;

import com.preethi.gedcom.models.Node;

public interface OutputWriter {

    public void write(Node rootNode);
}
