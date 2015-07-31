package Exceptions;

public class OtherExFoundException extends Exception {
    public OtherExFoundException() {
        super();
    }

    public OtherExFoundException(String message) {
        super(message);
    }

    public OtherExFoundException(Throwable cause) {
        super(cause);
    }

    public OtherExFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
