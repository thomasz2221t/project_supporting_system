package pl.polsl.projectmanagementsystem.exception;

public class MeetingNotFoundException extends RuntimeException{
    public MeetingNotFoundException(String message) {
        super(message);
    }
}
