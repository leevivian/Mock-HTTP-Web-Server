package WebServerCSC667;

import java.net.Socket;

/**
 * Created by rain2 on 2/3/2017.
 */
public class Worker extends Thread{
    private Socket client;
    private MimeTypes mimes;
    private HttpdConf config;

    public Worker(Socket socket, HttpdConf config, MimeTypes mimes){

    }

    //public run (){

    //}
}
