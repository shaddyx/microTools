package ua.org.shaddy.microtools.filetools;

public class ExecutionResult {
    final int code;
    final String data;
    public ExecutionResult(int code, String data) {
        super();
        this.code = code;
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public String getData() {
        return data;
    }
    public String toString() {
        return data;
    }
    
}
