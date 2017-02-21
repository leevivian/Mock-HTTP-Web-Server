package WebServerCSC667;

import WebServerCSC667.response.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    File filePath;

    public Logger (String fileName){
        filePath = new File(fileName);
    }

    // Return type?
    public void write (Request request, Response response){

        // Common log format from https://httpd.apache.org/docs/1.3/logs.html
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(filePath, true));
            // IP address of the client

            // Username, if password protected

            // The time the server finished processing the request
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss z");
            Date date = new Date();
            writer.println(dateFormat.format(date));

            // HTTP_Method Identifier HTTP_Version
            writer.println(request.getVerb() + " " + request.getURI() + " " + request.getHttpVersion());

            // Status Code
            writer.println(response.code);

            // Size of response, not including response headers
            if (response.getContentLength() > 0) {
                  writer.println(response.getContentLength());
             } else {
                  writer.println("-");
             }

             writer.println();
             writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
