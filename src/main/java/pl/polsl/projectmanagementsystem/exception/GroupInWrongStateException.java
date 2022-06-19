package pl.polsl.projectmanagementsystem.exception;

public class GroupInWrongStateException extends RuntimeException{
    public GroupInWrongStateException(String message) {
        super(message);
    }

}
