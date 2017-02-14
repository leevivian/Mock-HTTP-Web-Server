package WebServerCSC667;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by rain2 on 2/3/2017.
 */
public class Response {
    int code; // status code
    String reasonPhrase = "Response test";
    Resource resource;

    public Response (Resource resource){
        this.resource = resource;
        code = resource.getResponseCode();
        System.out.println(reasonPhrase);
    }

    //TODO: make send non void
    public void send(OutputStream out){
        try {
            out.write(reasonPhrase.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
