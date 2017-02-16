package WebServerCSC667;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public class Worker extends Thread{
    private Socket client;
    private MimeTypes mimes;
    private HttpdConf config;

    BufferedReader br;
    Stream.Builder<String> b;
    Stream<String> s;
    Request myReq;
    Resource res;

    String completeLine = "";

    public Worker(Socket socket, HttpdConf config, MimeTypes mimes) throws IOException{
        this.mimes = mimes;
        this.config = config;
        this.client = socket;

        String currentLine;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        b = Stream.builder();

        while((currentLine = br.readLine()) != null) {
            System.out.println(currentLine);

            b.add(currentLine + "\n");
            completeLine += currentLine;
        }

        run();
    }

    // Return type?
    public void run (){

        // Create request
        if (!completeLine.isEmpty()) {
            System.out.println("builder: " + b);
            s = b.build();
            System.out.println("Stream: " + s);
            myReq = new Request(s);
            myReq.printMe();
        }

        // Create resource
        try {
            res = new Resource(myReq.getURI(), config);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // Send response
        ResponseFactory rf = new ResponseFactory();
        rf.getResponse(myReq, res);
    }
}
