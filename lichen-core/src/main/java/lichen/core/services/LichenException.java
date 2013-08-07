package lichen.core.services;


import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * global lichen exception.
 *
 * @author jcai
 */
public class LichenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static LichenException wrap(Throwable exception,
                                       ErrorCode errorCode) {
        if (exception instanceof LichenException) {
            LichenException se = (LichenException) exception;
            if (errorCode != null && errorCode != se.getErrorCode()) {
                return new LichenException(exception.getMessage(),
                        exception, errorCode);
            }
            return se;
        } else {
            return new LichenException(exception.getMessage(),
                    exception, errorCode);
        }
    }

    public static LichenException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    private ErrorCode errorCode;

    public LichenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public LichenException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public LichenException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public LichenException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            StackTraceElement[] traces = getStackTrace();
            for (StackTraceElement trace : traces) {
                s.println("\tat " + trace);
            }
            if (getCause() != null) {
                getCause().printStackTrace(s);
            }
            s.flush();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (errorCode != null) {
            sb.append("LICHEN-").append(errorCode.getNumber()).append(":");
            sb.append(errorCode.toString()).append(" ");
        } else {
            sb.append("LICHEN-0000 UNKNOWN ");
        }
        if (getMessage() != null) {
            sb.append(getMessage());
        }
        if (getCause() != null) {
            sb.append(" -> ").append(getCause());
        }

        return sb.toString();
    }
}
