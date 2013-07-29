package lichen.ar.services;

/**
 * error code
 * @author jcai
 */
public enum ErrorCode {
    COLUMN_NOT_EXISTS(2001);

    private final int number;

    private ErrorCode(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
}
