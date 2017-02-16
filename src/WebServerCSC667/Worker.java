package WebServerCSC667;

import java.net.Socket;

public class Worker extends Thread{
    private Socket client;
    private MimeTypes mimes;
    private HttpdConf config;

    public Worker(Socket socket, HttpdConf config, MimeTypes mimes){
        this.mimes = mimes;
        this.config = config;
        this.client = socket;
    }

    // Return type?
    public void run (){
        Worker w = new Worker (client, config, mimes);
        w.start();
    }
}
