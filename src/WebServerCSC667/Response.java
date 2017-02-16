package WebServerCSC667;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Created by rain2 on 2/3/2017.
 */

/*HTTP_VERSION STATUS_CODE REASON_PHRASE
HTTP_HEADERS
BODY
• HTTP_VERSION is the current HTTP version (ex. HTTP/1.1)
• STATUS_CODE is a code defined by the protocol that communicates the
status of the response (we’ll look at some of the common status codes
over the next few slides, all are available on wiki)
• REASON_PHRASE is the descriptive text corresponding to the status code
• BODY is the requested resource content (optional, only present if the
Content-Length header is present)
*/
public class Response {
    int code; // status code
    String reasonPhrase = "Response test";
    Resource resource;
    private String httpVersion = "";


    public Response(int code){
        this.code = code;
        getReasonPhrase(code);
        System.out.println(reasonPhrase);

    }
    public Response (Resource resource){
        this.resource = resource;
        code = resource.getResponseCode();
        getReasonPhrase(code);
        System.out.println(reasonPhrase);

    }

    public void getReasonPhrase(int code){
        switch(code) {
            case 200:
                reasonPhrase = "OK";
                break;
            case 201:
                reasonPhrase = "Created";
                break;
            case 304:
                reasonPhrase = "No Content";
                break;
            case 400:
                reasonPhrase = "Bad Request";
                break;
            case 401:
                reasonPhrase = "Unauthorized";
                break;
            case 403:
                reasonPhrase = "Forbidden";
                break;
            case 404:
                reasonPhrase = "Not Found";
                break;
            case 500:
                reasonPhrase = "Internal Server Error";
                break;
            //TODO: Default??
            default:
                reasonPhrase = "ERROR: CODE NOT VALID";
        }
    }
    //TODO: make send non void
    public void send(OutputStream out){
        try {
            out.write(reasonPhrase.getBytes(Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args){
        Response test = new Response(400);
        System.out.println(test);
    }
}
