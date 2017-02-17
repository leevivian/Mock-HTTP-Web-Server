package WebServerCSC667;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.stream.Stream;

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
    private String httpVersion = "1.1";
    String responseString = "";

/*
HTTP/1.1 404 Not Found
Date: Sun, 18 Oct 2012 10:36:20 GMT
Server: Apache/2.2.14 (Win32)
Content-Length: 230
Connection: Closed
Content-Type: text/html; charset=iso-8859-1
 */

    public Response (Resource resource, int code){
        this.resource = resource;
        this.code = code;
        //
        reasonPhrase = getReasonPhrase(code);
        responseString = "HTTP/" + httpVersion + " " +code + " " + reasonPhrase +
                "\nDate: " + new Date() +
                "\nServer: " + "CSC667 WebServer" + //name?
                "\nContent-Length: " + //size of file
                "\nConnection: " + //keep-alive?
                "\nContent-Type: ";  //value of mimetype key
        //System.out.println(responseString);
    }

    public String getReasonPhrase(int code){
        switch(code) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 304:
                return "No Content";
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            //TODO: Default??
            default:
                return "ERROR: CODE NOT VALID";
        }
    }
    //TODO: make send non void
    public void send(OutputStream out){

        //out.write(responseString.getBytes(Charset.forName("UTF-8")));
        //PrintWriter pw = new PrintWriter(System.out);
        //pw.write(responseString);
        //byte[] bytes = responseString.getBytes();
        PrintWriter test = new PrintWriter( out, true );
        //System.out.println("bytes:" + bytes.length);



        test.println("jlkjlkjljkjlk");
        System.out.println("SEND TEST*********************");

    }

}
