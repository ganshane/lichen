package lichen.ar.services;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;
/**
 * @author jcai
 */
public class LichenActiveRecordException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static LichenActiveRecordException wrap(Throwable exception, ErrorCode errorCode) {
        if (exception instanceof LichenActiveRecordException) {
            LichenActiveRecordException se = (LichenActiveRecordException)exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new LichenActiveRecordException(exception.getMessage(), exception, errorCode);
            }
            return se;
        } else {
            return new LichenActiveRecordException(exception.getMessage(), exception, errorCode);
        }
    }

    public static LichenActiveRecordException wrap(Throwable exception) {
        return wrap(exception, null);
    }
    private ErrorCode errorCode;
    public LichenActiveRecordException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public LichenActiveRecordException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public LichenActiveRecordException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public LichenActiveRecordException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void printStackTrace(PrintStream s){
        synchronized(s){
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized(s){
            s.println(this);
            StackTraceElement[] traces = getStackTrace();
            for(StackTraceElement trace:traces){
                s.println("\tat " + trace);
            }
            if (getCause() != null) {
                getCause().printStackTrace(s);
            }
            s.flush();
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(errorCode != null){
            sb.append("LICHEN-AR-").append(errorCode.getNumber()).append(":");
            sb.append(errorCode.toString()).append(" ");
        }else{
            sb.append("LICHEN_AR-0000 UNKNOWN ");
        }
        if(getMessage()!= null){sb.append(getMessage());}
        if(getCause()!=null){sb.append(" -> ").append(getCause());}

        return sb.toString();
    }
}
