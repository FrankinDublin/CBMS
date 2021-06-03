package exception.Login;

public class wrongPwdException extends Exception{
    public wrongPwdException() {
    }

    public wrongPwdException(String message) {
        super(message);
    }
}
