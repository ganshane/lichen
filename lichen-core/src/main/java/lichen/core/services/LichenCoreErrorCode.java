package lichen.core.services;

/**
 * lichen core module exception code.
 *
 * @author jcai
 */
public enum LichenCoreErrorCode implements ErrorCode {
    UNSUPPORT_REMOVE_ITERATOR(1001);

    private final int number;

    private LichenCoreErrorCode(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
