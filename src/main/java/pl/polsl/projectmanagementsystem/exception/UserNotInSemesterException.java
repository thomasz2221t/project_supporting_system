package pl.polsl.projectmanagementsystem.exception;

public class UserNotInSemesterException extends RuntimeException{
    public UserNotInSemesterException(String message) {
        super(message);
    }
}
