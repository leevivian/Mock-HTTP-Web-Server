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
import java.util.stream.Stream;


public class Server {
    private HttpdConf configuration;
    private MimeTypes mimeTypes;
    private ServerSocket socket;
    private Dictionary accessFiles;
    private Socket connection;


    // for testing
    Request myReq;
    Boolean flag = false;

    public void start() throws IOException{
        configuration = new HttpdConf("httpd.conf");
        mimeTypes = new MimeTypes( "mime.types");

        socket = new ServerSocket(3100);



        while (true) {
            connection = socket.accept();
            String currentLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Stream.Builder<String> b = Stream.builder();

            while((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);

                b.add(currentLine + "\n");
            }

            if (flag) {
                Stream<String> s = b.build();
                myReq = new Request(s);
                myReq.printMe();

            }

            flag = true;
            connection.close();
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}
