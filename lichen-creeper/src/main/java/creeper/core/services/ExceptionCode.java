package creeper.core.services;

/**
 * exception code interface.
 * <p>
 *     系统各个模块自定义的异常代码，必须实现此接口,核心类实现的代码序列须在此进行说明
 * </p>
 * <table>
 *     <tr><td>模块</td><td>包结构</td><td>错误代码</td></tr>
 *     <tr><td>核心</td><td>creeper.core</td><td>1xxx</td></tr>
 *     <tr><td></td><td></td><td></td></tr>
 *     <tr><td></td><td></td><td></td></tr>
 * </table>
 * @author jcai
 */
public interface ExceptionCode {
    /**
     * 得到错误代码
     * @return 错误代码
     */
    int getNumber();
}
