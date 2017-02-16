package WebServerCSC667;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rain2 on 2/3/2017.
 */
public class Logger {
    File file;

    public Logger (String fileName){
        // Assuming fileName is a path
        // Creates a new File instance by converting the given pathname string into an abstract pathname.
        file = new File(fileName);
    }

    // Return type?
    public void write (Request request, Response response){

        // Common log format from https://httpd.apache.org/docs/1.3/logs.html
        try {
            PrintWriter writer = new PrintWriter(file, "UTF8");
            // IP address of the client

            // Username, if password protected

            // The time the server finished processing the request
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss z");
            Date date = new Date();
            writer.println(dateFormat.format(date));

            // HTTP_Method Identifier HTTP_Version
            writer.println(request.getVerb() + " " + request.getURI() + " " + request.getHttpVersion());

            // Status Code
            // maybe use getCode instead?
            writer.println(response.code);

            // Size of response, not including response headers
            // if (response's content-length > 0) {
            //      writer.println(reponse.getContentLength());
            // } else {
            //      writer.println("-");
            // }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
