package WebServerCSC667;

import WebServerCSC667.response.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    File filePath;
    public InetAddress inetAddress;

    public Logger (String fileName){
        filePath = new File(fileName);
    }

    public void write (Request request, Response response){

        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(filePath, true));

            writer.print(inetAddress.getLocalHost().getHostAddress() + " ");

            DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
            Date date = new Date();
            writer.print("[" + dateFormat.format(date) + "]" + " ");

            writer.print("\"" + request.getVerb() + " " + request.getURI() + " " +
                           request.getHttpVersion() + "\" ");

            writer.print(response.code + " ");

            if (response.getContentLength() > 0) {
                  writer.print(response.getContentLength());
             } else {
                  writer.print("-");
             }

             writer.println();
             writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
