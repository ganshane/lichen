package lichen.core.services;

/**
 * lichen core module exception code.
 *
 * @author jcai
 */
public enum LichenCoreErrorCode implements ErrorCode {
    UNSUPPORT_REMOVE_ITERATOR(1001);

    private final int _number;

    private LichenCoreErrorCode(final int number) {
        this._number = number;
    }

    public int getNumber() {
        return _number;
    }
}
