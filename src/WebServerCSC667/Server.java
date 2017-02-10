package WebServerCSC667;

import java.net.ServerSocket;
import java.util.Dictionary;

/**
 * Created by rain2 on 2/3/2017.
 */
public class Server {
    private HttpdConf configuration;
    private MimeTypes mimeTypes;
    private ServerSocket socket;
    private Dictionary accessFiles;

    public void start(){
        configuration = new HttpdConf("httpd.conf");
        mimeTypes = new MimeTypes( "mime.types");

    }
    public static void main(String[] args){
        System.out.print("hello");
        Server test = new Server();
        test.start();
    }
}
