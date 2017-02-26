package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class HeadResponse extends Response {
    public HeadResponse (Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(false);
    }

    @Override
    public void send(OutputStream out){
        PrintStream printStream = new PrintStream(out);

        printStream.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        printStream.println("Date: " + new Date());
        printStream.println("Server: " + getServerName());
        printStream.println("Content-Type: " + resource.getContentType());
        printStream.println("Content-Length: " +  resource.getBody().length);
        printStream.println("Last-Modified: " + resource.getLastModified());
        printStream.println();

        printStream.flush();
        printStream.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
