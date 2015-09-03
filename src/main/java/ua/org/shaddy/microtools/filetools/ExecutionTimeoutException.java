package ua.org.shaddy.microtools.filetools;


public class ExecutionTimeoutException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4863824434722914801L;

    public ExecutionTimeoutException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        
    }

    public ExecutionTimeoutException(String message, Throwable cause) {
        super(message, cause);
        
    }

    public ExecutionTimeoutException(String message) {
        super(message);
        
    }

    public ExecutionTimeoutException(Throwable cause) {
        super(cause);
        
    }

}
