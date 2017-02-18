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

            //Thread thread = new Worker(connection, configuration, mimeTypes);
            //thread.run();
            // *******

            String currentLine;
            completeLine = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            Stream.Builder<String> b = Stream.builder();

            while((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);

                b.add(currentLine + "\n");
                completeLine += currentLine;

                if (currentLine.isEmpty()) {
                    break;
                }
            }


            if (completeLine != "") {
                Stream<String> s = b.build();
                myReq = new Request(s);
                myReq.parse();

                // Create resource
                try {
                    res = new Resource(myReq.getURI(), configuration, mimeTypes);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                ResponseFactory rf = new ResponseFactory();

                try {
                    Response myResponse = rf.getResponse(myReq, res);
                    myResponse.send(connection.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            connection.close();
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}


/*

 // Send response
                ResponseFactory rf = new ResponseFactory();

                try {
                    Response myResponse = rf.getResponse(myReq, res);
                    myResponse.send(connection.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }



            OutputStream out = connection.getOutputStream();
            PrintStream ps = new PrintStream(out);
            ps.println("Hi I'm the output Stream <3");

            ps.flush();
            ps.close();

            try {
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
 */
