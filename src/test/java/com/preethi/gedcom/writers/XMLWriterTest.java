package com.preethi.gedcom.writers;

import com.preethi.gedcom.models.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XMLWriterTest {

    @Mock
    private Document document;
    @Mock
    private Transformer transformer;
    private XMLWriter xmlWriter;

    @Before
    public void setUp() throws Exception {
        xmlWriter = spy(XMLWriter.class);
        doReturn(document).when(xmlWriter).createEmptyDocument();
        doReturn(transformer).when(xmlWriter).buildTransformer();
        doNothing().when(transformer).transform(any(), any());
    }

    @Test
    public void shouldCreateXMLWithRootNode() throws Exception {
        Element mockRootElement = mock(Element.class);
        when(document.createElement("gedcom")).thenReturn(mockRootElement);

        xmlWriter.write(new Node(0, "gedcom"));

        verify(document).appendChild(mockRootElement);
        verify(transformer).transform(any(DOMSource.class), any(StreamResult.class));
    }

    @Test
    public void shouldCreateChildElementsWithIdUnderRootElement() throws Exception {
        Element mockRootElement = mock(Element.class);
        Node child = new Node(1, null, "name", "Preethi");
        Node node = new Node(0, "@IN001@", "INDI", null);
        node.addChild(child);
        Element nodeElement = mock(Element.class);
        Element childElement = mock(Element.class);
        when(document.createElement("INDI")).thenReturn(nodeElement);
        when(document.createElement("name")).thenReturn(childElement);

        xmlWriter.createChildElement(document, mockRootElement, node);

        verify(mockRootElement).appendChild(nodeElement);
        verify(nodeElement).setAttribute("id", "@IN001@");
        verify(nodeElement).appendChild(childElement);
        verify(childElement, never()).setAttribute("id", child.getId());
    }


    @Test
    public void shouldAddValueAsAttributeIfChildrenArePresent() throws Exception {
        Node node = new Node(1, null, "name", "Preethi");
        node.addChild(new Node(2, "first"));
        Element nodeElement = mock(Element.class);
        when(document.createElement("name")).thenReturn(nodeElement);

        xmlWriter.createChildElement(document, mock(Element.class), node);

        verify(nodeElement, times(1)).appendChild(any());
        verify(nodeElement).setAttribute("value", "Preethi");
    }


    @Test
    public void shouldAddValueAsTextNodeIfChildrenIsEmpty() throws Exception {
        Element mockRootElement = mock(Element.class);
        Node node = new Node(1, null, "name", "Preethi");
        Element nodeElement = mock(Element.class);
        when(document.createElement("name")).thenReturn(nodeElement);
        Text text = mock(Text.class);
        when(document.createTextNode("Preethi")).thenReturn(text);

        xmlWriter.createChildElement(document, mockRootElement, node);

        verify(nodeElement).appendChild(text);
        verify(nodeElement, never()).setAttribute("value", "Preethi");
    }


}
