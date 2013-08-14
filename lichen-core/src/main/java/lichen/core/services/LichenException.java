// Copyright 2013 the original author or authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package lichen.core.services;


import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

/**
 * 全局异常类,整个Lichen框架均使用这个异常类进行异常的抛出.
 * <p>
 * 所有异常消息均可以使用本异常类进行实现,
 * <ul>
 * <li>定义异常代码,譬如：{@link LichenCoreErrorCode}</li>
 * <li>throw new LichenException(LichenCoreErrorCode.UNSUPPORT_REMOVE_ITERATOR); </li>
 * <li>还可以设置一些需要观察的值，譬如：<br/>
 * <code>throw new LichenException(LichenCoreErrorCode.UNSUPPORT_REMOVE_ITERATOR).
 * set("userName",user.getName());</code>
 * </li>
 * </ul>
 * </p>
 * <p>
 * 每个编码为4位数字,已经加入的编码规则如下:
 * <table border="1">
 * <tr><th>模块</th><th>代码前缀</th><th>代码声明类</th></tr>
 * <tr><td>lichen-core</td><td>1</td><td>{@link lichen.core.services.LichenCoreErrorCode}</td></tr>
 * <tr><td>lichen-activerecord</td><td>2</td><td>{@link lichen.ar.services.ActiveRecordErrorCode}</td></tr>
 * <tr><td>lichen-jdbc</td><td>3</td><td>{@link lichen.jdbc.services.JdbcErrorCode}</td></tr>
 * </table>
 * </p>
 *
 * @author jcai
 */
public class LichenException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    //提供额外信息
    private final Map<String, String> _properties = new TreeMap<String, String>();
    //错误代码
    private ErrorCode _errorCode;

    /**
     * 通过给定的一个错误码来新建一个LichenException.
     *
     * @param errorCode 错误码
     */
    public LichenException(final ErrorCode errorCode) {
        this._errorCode = errorCode;
    }

    /**
     * 通过给定的异常消息，以及错误码来构建异常消息.
     *
     * @param message   异常消息
     * @param errorCode 错误码
     */
    public LichenException(final String message, final ErrorCode errorCode) {
        super(message);
        this._errorCode = errorCode;
    }

    /**
     * 通过给定的异常消息以及原始的异常消息和错误码来创建一个异常消息类.
     *
     * @param message   异常消息
     * @param cause     根异常
     * @param errorCode 错误码
     */
    private LichenException(final String message,
                            final Throwable cause,
                            final ErrorCode errorCode) {
        super(message, cause);
        this._errorCode = errorCode;
    }

    /**
     * 包装一个异常类.
     * <p>
     * 如果此异常是LichenException，并且和errorCode一样，则返回 exception.<br/>
     * 否则，则对此异常进行包装
     * </p>
     *
     * @param exception 待包装的异常消息类
     * @param errorCode 错误码
     * @return 包装后的异常消息
     */
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

    /**
     * 包装一个成LichenException异常.
     *
     * @param exception 异常
     * @return 包装后的LichenException
     * @see #wrap(Throwable, ErrorCode).
     */
    public static LichenException wrap(final Throwable exception) {
        return wrap(exception, null);
    }

    /**
     * 得到错误码.
     *
     * @return 错误码
     */
    public final ErrorCode getErrorCode() {
        return _errorCode;
    }

    /**
     * 设置debug使用的值.
     *
     * @param key   key
     * @param value 值对象
     * @return 本对象
     */
    public final LichenException set(String key, Object value) {
        _properties.put(key, String.valueOf(value));
        return this;
    }

    /**
     * 输出异常消息.
     *
     * @param printStream 输出流
     */
    public final void printStackTrace(final PrintStream printStream) {
        synchronized (printStream) {
            printStackTrace(new PrintWriter(printStream));
        }
    }

    /**
     * 输出堆栈消息.
     *
     * @param printWriter 输出流
     */
    public final void printStackTrace(final PrintWriter printWriter) {
        synchronized (printWriter) {
            printWriter.println(this);
            for (String key : _properties.keySet()) {
                printWriter.println("\t" + key + "=[" + _properties.get(key) + "]");
            }

            StackTraceElement[] traces = getStackTrace();
            for (StackTraceElement trace : traces) {
                printWriter.println("\tat " + trace);
            }
            if (getCause() != null) {
                getCause().printStackTrace(printWriter);
            }
            printWriter.flush();
        }
    }

    /**
     * @see Object#toString()
     */
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
