package pl.polsl.projectmanagementsystem.exception;

public class UsernameOrEmailTakenException extends RuntimeException {
    public UsernameOrEmailTakenException(String msg) {
        super(msg);
    }
}
