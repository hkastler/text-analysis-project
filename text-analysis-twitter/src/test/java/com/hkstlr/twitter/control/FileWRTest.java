package com.hkstlr.twitter.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author henry.kastler
 */
public class FileWRTest {
    
    private static final Logger LOG = Logger.getLogger(FileWRTest.class.getName());

    FileWR cut;//class under test

    public FileWRTest() {
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() {
    }

    @Test
    public void testWriteFile() throws Exception {
        LOG.info("writeFile");
        File temp = folder.newFile();
        String text = "hello world";
        cut = new FileWR(temp.getPath());
        cut.writeFile(text);

        String currentLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(temp))) {
            currentLine = reader.readLine();
        }
        
        assertEquals(text,currentLine);
        assertTrue(temp.delete());
    }
    
    @Test(expected = IOException.class)
    public void testWriteFileBadLoc() throws Exception {
        LOG.info("testWriteFileBadLoc");
        File temp = folder.newFile();
        String text = "hello world";
        String badLoc = temp.getPath().concat("/dev/null/");
        cut = new FileWR(badLoc);
        cut.writeFile(text);

        String currentLine;
        try (BufferedReader reader = new BufferedReader(new FileReader(temp))) {
            currentLine = reader.readLine();
        }
        
        assertNull(currentLine);
        assertTrue(temp.delete());
    }
    
    @Test(expected = NullPointerException.class)
    public void testWriteFileNull() throws Exception {
        LOG.info("testWriteFileNull");
        File temp = folder.newFile();
        String loc = temp.getPath();
        cut = new FileWR(loc);
        cut.writeFile(null);
        assertTrue(temp.delete());
    }

    @Test
    public void testGetDesktopFilePath() {
        LOG.info("getDesktopFilePath");
        String fileNameBase = "test";
        String fileExtension = ".txt";
        String expResult = System.getProperty("user.home")
                .concat(File.separator)
                .concat("Desktop")
                .concat(File.separator)
                .concat(fileNameBase);
        String result = FileWR.getDesktopFilePath(fileNameBase, fileExtension);
        assertTrue(result.contains(expResult));
        assertTrue(result.endsWith(fileExtension));
        
        fileNameBase = "?:test//";
        expResult = System.getProperty("user.home")
                .concat(File.separator)
                .concat("Desktop")
                .concat(File.separator)
                .concat("test");
        result = FileWR.getDesktopFilePath(fileNameBase, fileExtension);
        assertTrue(result.contains(expResult));        
        assertTrue(!result.contains("?"));
    }
    

}
