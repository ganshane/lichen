package creeper.core.services;

/**
 * creeper core exception code
 * @author jcai
 */
public enum CreeperCoreExceptionCode implements ExceptionCode {
    FAIL_READ_CONFIG_FILE(1001),FAIL_XXX(1002);

    private final int _num;
    private CreeperCoreExceptionCode(int num){
        this._num = num;
    }
    @Override
    public int getNumber() {
        return _num;
    }
}
