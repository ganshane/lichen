package creeper.core.services;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * creeper exception.
 * <p>
 *     整个系统的异常处理框架.
 *     <ul>
 *         <li>每种可知异常按照对应的代码进行标注</li>
 *         <li>可以封装异常变为RuntimeException</li>
 *     </ul>
 * </p>
 * @author jcai
 */
public class CreeperException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static CreeperException wrap(Throwable exception, ExceptionCode exceptionCode) {
        if (exception instanceof CreeperException) {
            CreeperException se = (CreeperException) exception;
            if (exceptionCode != null && exceptionCode != se.getExceptionCode()) {
                return new CreeperException(exception.getMessage(), exception, exceptionCode);
            }
            return se;
        } else {
            return new CreeperException(exception.getMessage(), exception, exceptionCode);
        }
    }

    public static CreeperException wrap(Throwable exception) {
        return wrap(exception, null);
    }

    private ExceptionCode exceptionCode;
    private final Map<String, Object> properties = new TreeMap<String, Object>();

    public CreeperException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public CreeperException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public CreeperException(Throwable cause, ExceptionCode exceptionCode) {
        super(cause);
        this.exceptionCode = exceptionCode;
    }

    public CreeperException(String message, Throwable cause, ExceptionCode exceptionCode) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public CreeperException setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
        return this;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) properties.get(name);
    }

    public CreeperException set(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    public void printStackTrace(PrintStream s) {
        synchronized (s) {
            printStackTrace(new PrintWriter(s));
        }
    }

    public void printStackTrace(PrintWriter s) {
        synchronized (s) {
            s.println(this);
            s.println("\t-------------------------------");
            if (exceptionCode != null) {
                s.println("\tCREEPER-"+exceptionCode.getNumber() +":"+ exceptionCode + "@" + exceptionCode.getClass().getName());
            }
            for (String key : properties.keySet()) {
                s.println("\t" + key + "=[" + properties.get(key) + "]");
            }
            s.println("\t-------------------------------");
            StackTraceElement[] trace = getStackTrace();
            for (int i = 0; i < trace.length; i++)
                s.println("\tat " + trace[i]);

            Throwable ourCause = getCause();
            if (ourCause != null) {
                ourCause.printStackTrace(s);
            }
            s.flush();
        }
    }

    @Override
   public String toString(){
        StringBuilder sb  = new StringBuilder();
        if(exceptionCode != null){
            sb.append("CREEPER-").append(exceptionCode.getNumber()).append(":");
            sb.append(exceptionCode).append(" ");
        }else{
            sb.append("CREEPER-0000 UNKNOWN ");
        }
        if(super.getMessage() != null){sb.append(super.getMessage());}
        if(super.getCause() !=null){sb.append(" -> ").append(super.getCause());}

        return sb.toString();
    }

}

