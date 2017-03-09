package exception;

/**
 * @author Chenli
 * @time 2017/3/5 15:44
 */
public class ParterException extends Exception {

    public ParterException() {
    }

    public ParterException(String message) {
        super(message);
    }

    public ParterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParterException(Throwable cause) {
        super(cause);
    }

    public ParterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
