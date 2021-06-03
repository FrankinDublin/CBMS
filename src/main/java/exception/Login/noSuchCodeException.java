package exception.Login;

public class noSuchCodeException extends Exception{

    public noSuchCodeException() {
    }

    public noSuchCodeException(String message) {
        super(message);
    }
}
