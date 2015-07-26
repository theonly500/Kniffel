package Exceptions;

public class NoDiceFoundException extends Exception {
    public NoDiceFoundException(){
        super();
    }

    public NoDiceFoundException(String message){
        super(message);
    }

    public NoDiceFoundException(Throwable cause){
        super(cause);
    }

    public NoDiceFoundException(String message,Throwable cause){
        super(message, cause);
    }
}
