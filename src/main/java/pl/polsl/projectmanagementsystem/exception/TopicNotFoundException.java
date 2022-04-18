package pl.polsl.projectmanagementsystem.exception;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String msg) {
        super(msg);
    }
}
