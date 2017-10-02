package com.idklabs.app;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.*;

public class LinkedListTest {
   
    private LinkedListGrid example = new LinkedListGrid(7);
    
    @Test
    public void testList() {
        assertEquals("Should start with size of 1", 1, example.getSize());
        assertNotNull("Should start with 1 node", example.getNode(1, 1));
    }
    
    @Test
    public void testValue() {
        example.addLayer();
        assertEquals("Adding 1 layer should grow grid to 3", 3, example.getSize());
        
        testValue(1, 2, "X");
        testValue(2, 1, "Y");
        testValue(2, 2, "Z");
    }
    
    @Test
    public void testMax() {
        example.addLayer();
        example.addLayer();
        example.addLayer();
        assertEquals("Adding 3 layers should grow grid to 7", 7, example.getSize());
        
        try {
            example.addLayer();
            fail("Expected additional layer to exceed max.");
        } catch(IllegalStateException e) {
            assertEquals("The grid has reached its maximum size.", e.getMessage());
        }
        
    }
    
    private void testValue(int x, int y, Object value) {
        example.getNode(x, y).setValue(value);
        assertEquals("The value set in node should equal the value retrieved", value, example.getNode(x, y).getValue());
    }
}
