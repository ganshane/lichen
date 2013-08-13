package lichen.ar.services;

import lichen.core.services.ErrorCode;

/**
 * error code.
 * @author jcai
 */
public enum ActiveRecordErrorCode implements ErrorCode {
    COLUMN_NOT_EXISTS(2001);

    private final int number;

    private ActiveRecordErrorCode(final int vnumber) {
        this.number = vnumber;
    }
    public int getNumber() {
        return number;
    }
}
