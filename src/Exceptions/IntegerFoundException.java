package Exceptions;

public class IntegerFoundException extends Exception {
    public IntegerFoundException(){
        super();
    }

    public IntegerFoundException(String message){
        super(message);
    }

    public IntegerFoundException(Throwable cause){
        super(cause);
    }

    public IntegerFoundException(String message,Throwable cause){
        super(message, cause);
    }
}
