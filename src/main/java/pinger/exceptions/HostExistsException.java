package pinger.exceptions;

public class HostExistsException extends Exception {
    private static final String MESSAGE = "Данный север уже существует!";

    public HostExistsException() {
        super(MESSAGE);
    }
}
