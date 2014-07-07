package lichen.creeper.core.services;

import lichen.core.services.ExceptionCode;

/**
 * lichen.creeper core exception code
 * @author jcai
 */

public enum CreeperCoreExceptionCode implements ExceptionCode {
    FAIL_READ_CONFIG_FILE(1001),
    FAIL_CONFIG_PROXOOL(1002),
    FAIL_MIGRAT_SCRIPT(1003),
    ACCESS_DENIED(1004),
    URL_AND_PAGE_CLASS_IS_NULL(1005),
    FAIL_PARSE_XML(1006);

    private final int _num;
    private CreeperCoreExceptionCode(int num){
        this._num = num;
    }
    @Override
    public int getNumber() {
        return _num;
    }
}
