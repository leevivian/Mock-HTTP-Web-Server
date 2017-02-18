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
        super();
        this.mimes = mimes;
        this.config = config;
        this.client = socket;

        String currentLine;
        completeLine = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Stream.Builder<String> b = Stream.builder();

        while((currentLine = br.readLine()) != null) {

            b.accept(currentLine + "\n");
            completeLine += currentLine;

            if (currentLine.isEmpty()) {
                break;
            }
        }

        if (completeLine != "") {
            s = b.build();
            run();
        }
    }

    public void run(){
        myReq = new Request(s);
        myReq.parse();

        // Create resource
        try {
            res = new Resource(myReq.getURI(), config, mimes);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseFactory rf = new ResponseFactory();

        try {
            Response myResponse = rf.getResponse(myReq, res);
            myResponse.send(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
