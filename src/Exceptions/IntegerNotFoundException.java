package Exceptions;

public class IntegerNotFoundException extends Exception {
    public IntegerNotFoundException(){
        super();
    }

    public IntegerNotFoundException(String message){
        super(message);
    }

    public IntegerNotFoundException(Throwable cause){
        super(cause);
    }

    public IntegerNotFoundException(String message,Throwable cause){
        super(message, cause);
    }
}
