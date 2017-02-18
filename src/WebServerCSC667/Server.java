package WebServerCSC667;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Dictionary;
import java.lang.Exception;
import java.util.Iterator;
import java.util.stream.Stream;


public class Server {
    private HttpdConf configuration;
    private MimeTypes mimeTypes;
    private ServerSocket socket;
    private Dictionary accessFiles;

    String completeLine = "";
    Resource res;

    // for testing
    Request myReq;

    public void start() throws IOException{
        configuration = new HttpdConf("httpd.conf");
        mimeTypes = new MimeTypes( "mime.types");

        socket = new ServerSocket(configuration.getPort());
        Socket connection = null;

        while (true) {
            connection = socket.accept();

            Worker thread = new Worker(connection, configuration, mimeTypes);
            // *******

            connection.close();
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}

