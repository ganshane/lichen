package lichen.migration.internal;

/**
 * migration exception
 * @author jcai
 */
public class MigrationException extends RuntimeException{
    public MigrationException(String message){
        super(message);
    }

    public MigrationException(Throwable throwable) {
        super(throwable);
    }
}
