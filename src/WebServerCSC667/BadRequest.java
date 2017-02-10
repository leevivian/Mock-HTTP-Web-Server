package WebServerCSC667;

public class BadRequest extends Exception{

    String statusCode;
    public BadRequest() {
        statusCode = "400";
    }

}
