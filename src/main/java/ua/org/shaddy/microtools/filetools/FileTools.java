package ua.org.shaddy.microtools.filetools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ua.org.shaddy.microtools.ThreadTools;

public class FileTools {
    private static final String RESOURCES_PREFIX = "resources:";
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final long EXECUTION_ITERATION_TIMEOUT = 100;
    private static String DEFAULT_CHARSET = "utf-8";

    public static void filePutContent(String name, byte[] bytes) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(getOutputStream(name));
            bos.write(bytes);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            throw new RuntimeException("Error saving file", e);
        }
    }

    /**
     * returns true if file exists
     * 
     * @param fileName
     * @return
     */
    public static boolean exists(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * returns long value from file
     * 
     * @param name
     *            - file name
     * @return
     */
    public static long fileGetLong(String name) {
        String res = fileGetString(name, DEFAULT_CHARSET);
        return Long.parseLong(res.trim());
    }

    /**
     * saves data to the file
     * 
     * @param name
     * @param data
     */
    public static void filePutContent(String name, String data) {
        filePutContent(name, data, DEFAULT_CHARSET);
    }

    /**
     * saves data to the file
     * 
     * @param name
     * @param data
     */
    public static void filePutContent(String name, String data, String charset) {
        try {
            filePutContent(name, data.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error writing file", e);
        }
    }

    /**
     * reads a file to string
     * 
     * @param name
     * @return
     */
    public static String fileGetString(String name) {
        return fileGetString(name, DEFAULT_CHARSET);
    }

    /**
     * reads a file to string
     * 
     * @param name
     *            - file name
     * @return file contents
     */
    public static String fileGetString(String name, String encoding) {
        BufferedReader reader = null;
        try {
            InputStream is = getInputStream(name);
            if (is == null){
                throw new FileToolsException("file:" + name + " is not exists");
            }
            reader = new BufferedReader(new InputStreamReader(is, encoding));
            StringBuilder sb = new StringBuilder();
            String ls = System.getProperty("line.separator");
            // sb.append()
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing file", e);
                }
            }
        }
    }

    /**
     * returns the list of strings from file
     * 
     * @param file
     * @param encoing
     * @return
     */
    public static List<String> fileGetArray(File file, String encoing) {
        List<String> result = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e1) {
            throw new FileToolsException("File not found:" + file.getAbsolutePath());
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
    /**
     * reads a file to byte array
     * 
     * @param path
     *            - file path
     * @return file contents
     */
    public static byte[] fileGetBytes(String path) {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            ous = new ByteArrayOutputStream();
            ios = getInputStream(path);
            int read = 0;
            try {
                while ((read = ios.read(buffer)) != -1) {
                    ous.write(buffer, 0, read);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading file", e);
            }
        } finally {
            try {
                if (ous != null) ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null) ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
    /**
     * returns input stream from path
     * @param path
     * @return
     */
    public static InputStream getInputStream(String path){
        InputStream is = null;
        if (path.startsWith(RESOURCES_PREFIX)){
            ClassLoader classLoader = FileTools.class.getClassLoader();
            String cleanPath = path.substring(RESOURCES_PREFIX.length());
            is = classLoader.getResourceAsStream(cleanPath);
        } else {
            try {
                is = new FileInputStream(path);
            } catch (FileNotFoundException e) {
               throw new FileToolsException("Error opening path:" + path);
            }
        }
        return is;
    }
    
    /**
     * returns output stream from path
     * @param path
     * @return
     */
    public static OutputStream getOutputStream(String path){
        OutputStream is = null;
        if (path.startsWith(RESOURCES_PREFIX)){
            ClassLoader classLoader = FileTools.class.getClassLoader();
            try {
                is = new FileOutputStream(classLoader.getResource(path.substring(RESOURCES_PREFIX.length())).getPath());
            } catch (FileNotFoundException e) {
                throw new FileToolsException("Error opening path for wrighting:" + path);
            }
        } else {
            try {
                is = new FileOutputStream(path);
            } catch (FileNotFoundException e) {
               throw new FileToolsException("Error opening path for wrighting:" + path);
            }
        }
        return is;
    }

    /**
     * executes path and returns execution result
     * 
     * @param path
     * @return
     */
    public static ExecutionResult execute(String path) {
        try {
            return execute(path, -1);
        } catch (ExecutionTimeoutException e) {
            throw new FileToolsException("Execution timeout (it cant be)");
        }
    }
    
    public static ExecutionResult execute(String path, long timeOut) throws ExecutionTimeoutException {
        ExecuteTimeoutFlag flag = new ExecuteTimeoutFlag();
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            process = runtime.exec(path);
        } catch (IOException e1) {
            return null;
        }
        ProcessStreamReader stdOutReader = new ProcessStreamReader(process.getInputStream());
        ProcessStreamReader stdErrReader = new ProcessStreamReader(process.getErrorStream());
        
        if (timeOut > 0){
            flag.startTimer(timeOut);
        } 
        stdOutReader.start();
        stdErrReader.start();
        int exitValue = -1;
        while (stdOutReader.isStarted() || stdErrReader.isStarted()) {
            if (flag.isTimeoutReached()){
                process.destroy();
                stdOutReader.waitForFullRead();
                stdErrReader.waitForFullRead();
                stdErrReader.stop();
                stdOutReader.stop();
                break;
            }
            try{
                exitValue = process.exitValue();
            } catch (IllegalThreadStateException e){
                //
                //  process is not ready yet
                //
                ThreadTools.safeSleep(EXECUTION_ITERATION_TIMEOUT);
            }
        }
        if (timeOut > 0){
            flag.stopTimer();
        } 
        return new ExecutionResult(exitValue, stdOutReader.toString() + stdErrReader.toString());
    }
    public static void removeFile(String path) {
        File file = new File(path);
        if (!file.delete()){
            throw new FileToolsException("File is not deleted:" + path);
        }
    }
}
