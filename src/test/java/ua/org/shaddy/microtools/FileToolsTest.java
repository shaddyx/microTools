package ua.org.shaddy.microtools;

import static org.junit.Assert.*;

import org.junit.Test;

import ua.org.shaddy.microtools.filetools.FileTools;

public class FileToolsTest {
    private static final String TEST_FILE_NAME = "test.data";
    
    @Test
    public void testFilePutContentStringByteArray() {
        byte[] testBytes = new byte[] {1, 2, 3, 4};
        FileTools.filePutContent(TEST_FILE_NAME, testBytes);
        assertEquals(true, FileTools.exists(TEST_FILE_NAME));
        assertArrayEquals(testBytes, FileTools.fileGetBytes(TEST_FILE_NAME));
        FileTools.removeFile(TEST_FILE_NAME);
    }

    @Test
    public void testFileGetLong() {
        assertEquals(23, FileTools.fileGetLong("resources:longInFile.txt"));
    }

    @Test
    public void testGetInputStream() {
        assertNotNull(FileTools.getInputStream("resources:longInFile.txt"));
    }

    @Test
    public void testGetOutputStream() {
        assertNotNull(FileTools.getOutputStream("resources:longInFile.txt"));
    }

}
