package ua.org.shaddy.microtools;


public class ThreadTools {
    /**
     * starts new thread
     * @param service
     * @return
     */
    public static Thread startThread(Runnable service){
        return startThread(service, null);
    }
    /**
     * starts new thread
     * @param service
     * @param threadName
     * @return
     */
    public static Thread startThread(Runnable service, String threadName){
        Thread thread;
        if (threadName != null){
            thread = new Thread(service, threadName);
        } else {
            thread = new Thread(service);
        }
        thread.start();
        return thread;
    }
    /**
     * sleeps for ms miliseconds
     * @param ms
     */
    public static void safeSleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new MicroToolsException("Interrupted", e);
        }
        
    }
    
}
