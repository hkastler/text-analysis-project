package com.hkstlr.rest.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.rules.TemporaryFolder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author henry.kastler
 */
public class WriteServiceEventHandlerTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    WriteServiceEventHandler cut;

    public WriteServiceEventHandlerTest() {
        super();
    }

    @Before
    public void setUp() {
       
    }

    /**
     * Test of handle method, of class WriteServiceEventHandler.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testHandle() throws Exception {
        cut = spy(WriteServiceEventHandler.class);
        File temp = folder.newFile();
        String[] payload = getPayload(temp);       
        
        WriteServiceEvent wse = new WriteServiceEvent();
        wse.setName("addTrainText");        
        wse.setPayload(payload);
        
        cut.handle(wse);        
        verify(cut, times(1)).addTrainText(payload);
    }

    @Test
    public void testAddTrainText() throws IOException {

        File temp = folder.newFile();
        String[] payload = getPayload(temp);
        
        cut = new WriteServiceEventHandler();
        cut.addTrainText(payload);

        try (Stream linesStream = Files.lines(temp.toPath())) {
            
            linesStream.forEach(line -> {
                String l = (String)line;
                if(l.length()>0){
                 assertTrue(l.contains(payload[0]));                 
                }              
            });
        }
    }
    
    private String[] getPayload(File file) throws IOException{
        
        String sentiment = "positive";
        String text = "hello world";
        
        String[] payload = new String[3];
        payload[0] = sentiment;
        payload[1] = text;
        payload[2] = file.getAbsolutePath();
        return payload;
    }

}
