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
            getRequest(connection);
            connection.close();
        }
    }

    private void getRequest(Socket mySocket) throws IOException {
        System.out.println("This is an HTTP Request from Postman");

        String currentLine;
        String completeLine = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

        while((currentLine = br.readLine()) != null) {
            System.out.println(currentLine);

            // for Testing Request's Constructor with String
            completeLine += currentLine + "\n";
        }


        if (completeLine != null) {
            try {
                myReq = new Request(completeLine);
                myReq.printMe();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}
