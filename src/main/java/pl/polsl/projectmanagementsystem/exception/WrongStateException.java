package pl.polsl.projectmanagementsystem.exception;

public class WrongStateException extends RuntimeException{
    public WrongStateException(String msg){
        super(msg);
    }
}
