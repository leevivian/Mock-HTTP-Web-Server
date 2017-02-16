package WebServerCSC667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
    private Socket connection;


    // for testing
    Request myReq;

    public void start() throws IOException{
        configuration = new HttpdConf("httpd.conf");
        mimeTypes = new MimeTypes( "mime.types");

        socket = new ServerSocket(configuration.getPort());



        while (true) {
            connection = socket.accept();
            String currentLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Stream.Builder<String> b = Stream.builder();

            String completeLine = "";

            while((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);

                b.add(currentLine + "\n");
                completeLine += currentLine;
            }

            if (!completeLine.isEmpty()) {
                System.out.println("builder: " + b);
                Stream<String> s = b.build();
                System.out.println("Stream: " + s);
                myReq = new Request(s);
                myReq.printMe();
            }


            // Unsure about this location of this...
            Worker newWorker = new Worker(connection, configuration, mimeTypes);

            connection.close();
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}
