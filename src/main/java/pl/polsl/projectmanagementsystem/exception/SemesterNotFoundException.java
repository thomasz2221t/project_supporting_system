package pl.polsl.projectmanagementsystem.exception;

public class SemesterNotFoundException extends RuntimeException{
    public SemesterNotFoundException(String message) {
        super(message);
    }
}
