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

    public static LichenException wrap(final Throwable exception,
                                       final ErrorCode errorCode) {
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

    public static LichenException wrap(final Throwable exception) {
        return wrap(exception, null);
    }

    private ErrorCode _errorCode;

    public LichenException(final ErrorCode errorCode) {
        this._errorCode = errorCode;
    }

    public LichenException(final String message, final ErrorCode errorCode) {
        super(message);
        this._errorCode = errorCode;
    }

    public LichenException(final Throwable cause, final ErrorCode errorCode) {
        super(cause);
        this._errorCode = errorCode;
    }

    public LichenException(final String message,
                           final Throwable cause,
                           final ErrorCode errorCode) {
        super(message, cause);
        this._errorCode = errorCode;
    }

    public final ErrorCode getErrorCode() {
        return _errorCode;
    }

    public final void printStackTrace(final PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public final void printStackTrace(final PrintWriter s) {
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

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (_errorCode != null) {
            sb.append("LICHEN-").append(_errorCode.getNumber()).append(":");
            sb.append(_errorCode.toString()).append(" ");
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
