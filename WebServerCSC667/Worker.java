package WebServerCSC667;

import WebServerCSC667.Exception.BadRequest;
import WebServerCSC667.Exception.InternalServerError;
import WebServerCSC667.configuration.HttpdConf;
import WebServerCSC667.configuration.Logger;
import WebServerCSC667.configuration.MimeTypes;
import WebServerCSC667.response.*;

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
    private Logger logger;
    private Stream<String> completeStream;
    private Request request;
    private Response response;
    private Resource resource;
    private String clientCompleteInput = "";

    public Worker(Socket socket, HttpdConf config, MimeTypes mimes, Logger logAction) throws IOException{
        super();
        this.mimes = mimes;
        this.config = config;
        this.client = socket;
        this.logger = logAction;

        String clientInputStreamCurrentLine;
        clientCompleteInput = "";
        BufferedReader readClientInputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
        Stream.Builder<String> builder = Stream.builder();

        while((clientInputStreamCurrentLine = readClientInputStream.readLine()) != null) {

            builder.accept(clientInputStreamCurrentLine + "\n");
            clientCompleteInput += clientInputStreamCurrentLine;

            if (clientInputStreamCurrentLine.isEmpty()) {
                break;
            }
        }

        if (clientCompleteInput != "") {
            completeStream = builder.build();
            try {
                parseRequest();
            } catch (RuntimeException e) {
                e.printStackTrace();
                throw new InternalServerError(client);
            }
        }
    }

    public void parseRequest() {

        try {
            request = new Request(completeStream);
            request.parseRequestString();

            if (request.flagRequestParsingException) {
                throw new BadRequest(client);
            } else {
                run();
            }
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        }
    }

    public void run(){

        try {
            resource = new Resource(request.getURI(), config, mimes);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseFactory responseFactory = new ResponseFactory();

        try {
            response = responseFactory.getResponse(request, resource);
            response.send(client.getOutputStream());
            logger.write(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
