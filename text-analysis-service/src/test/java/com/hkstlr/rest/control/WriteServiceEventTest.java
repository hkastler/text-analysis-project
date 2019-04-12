
package com.hkstlr.rest.control;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author henry.kastler
 */
public class WriteServiceEventTest {
    
    WriteServiceEvent cut;
    
    public WriteServiceEventTest() {
        super();
    }
    
    @Before
    public void setUp() {
        cut = new WriteServiceEvent();
    }

    @Test
    public void testWriteServiceEvent() {
        String name = "test";
        String[] payload = new String[3];
        cut.setName("test");
        cut.setPayload(payload);
        
        assertEquals(name, cut.getName());
        assertArrayEquals(payload, (String[])cut.getPayload());
    }
    
}
