package ua.org.shaddy.microtools.filetools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ua.org.shaddy.microtools.ThreadTools;

class ProcessStreamReader implements Runnable {
    private final BufferedReader buffer;
    private final StringBuilder sb = new StringBuilder();
    private volatile boolean started = false;
    private Thread thread;
    private final InputStream inputStream;

    public ProcessStreamReader(InputStream is) {
        this.inputStream = is;
        buffer = new BufferedReader(new InputStreamReader(is));
            
    }
    
    public void run() {
    	started = true;
        int intChar;
        try {
            while ((intChar = buffer.read()) != -1){
                sb.append((char) intChar);
            }
        } catch (Exception e) {
            //logger.error("Error reading process stream", e);
        } finally {
        	started = false;
        }
    }
    
    public void start(){
        started = true;
        thread = ThreadTools.startThread(this, "ProcessStreamReader");
    }
    
    public void stop(){
        started = false;
        if (buffer != null){
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }

        thread.interrupt();
    }
    
    public boolean isStarted() {
        return started;
    }
    
    public void waitForFullRead(){
    	while (started){
    		ThreadTools.safeSleep(1);
    	}
    }
    
    public String toString(){
        return sb.toString();
    }

}
