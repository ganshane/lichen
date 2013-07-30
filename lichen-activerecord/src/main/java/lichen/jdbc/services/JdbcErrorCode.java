package lichen.jdbc.services;

import lichen.core.services.ErrorCode;

/**
 * jdbc module error code definition
 * @author jcai
 */
public enum JdbcErrorCode implements ErrorCode{
    DATA_ACCESS_ERROR(3001),
    DRIVER_NOT_FOUND(3002),
    NO_TRANSACTION_IN_CURRENT_THREAD(3003)
    ;

    private int errorCode;
    private JdbcErrorCode(int code) {
        this.errorCode = code;
    }

    @Override
    public int getNumber() {
        return this.errorCode;
    }
}
