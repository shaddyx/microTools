package ua.org.shaddy.microtools.filetools;

import java.util.TimerTask;

import ua.org.shaddy.microtools.TimerTools;

class ExecuteTimeoutFlag {
    private volatile boolean timeoutReached = false;
    private volatile TimerTask timeoutObject = null;

    public boolean isTimeoutReached() {
        return timeoutReached;
    }
    
    public synchronized void startTimer(long timeOut){
        timeoutObject = TimerTools.setTimeout(new Runnable() {
            public void run() {
                timeoutReached = true;
            }
        }, timeOut);
    }
    
    public synchronized void stopTimer(){
        if (timeoutObject != null){
            TimerTools.clearTimeout(timeoutObject);
        }
    }
   
}
