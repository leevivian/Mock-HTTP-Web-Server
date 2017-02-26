import WebServerCSC667.configuration.HttpdConf;
import WebServerCSC667.configuration.Logger;
import WebServerCSC667.configuration.MimeTypes;
import WebServerCSC667.Worker;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Dictionary;

public class Server {
    private HttpdConf configuration;
    private MimeTypes mimeTypes;
    private ServerSocket socket;
    private Dictionary accessFiles;

    public void start() throws IOException{
        configuration = new HttpdConf("httpd.conf");
        mimeTypes = new MimeTypes( "mime.types");

        socket = new ServerSocket(configuration.getPort());
        Socket connection = null;
        Logger logAction = new Logger(configuration.getLogFileLocation());

        while (true) {
            connection = socket.accept();
            new Worker(connection, configuration, mimeTypes, logAction);
            connection.close();
        }
    }


    public static void main(String[] args) throws IOException{
        System.out.println("hello");
        Server test = new Server();
        test.start();
    }

}

