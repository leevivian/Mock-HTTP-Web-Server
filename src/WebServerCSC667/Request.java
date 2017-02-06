package WebServerCSC667;

import java.util.*;
import java.util.stream.Stream;

public class Request {
    private String uri;
    private String body;
    private String verb;
    private String httpVersion;
    private HashMap<String, String> headers;

    private Object myStr;

    public Request (String test){
        myStr = test;
        parse();
    }
    public Request (Stream client){

        parse();
    }
    public void parse (){
        Boolean contentLength = false;
        String[] arr = (myStr.toString()).split("\n");

        // HTTP_METHOD IDENTIFIER HTTP_VERSION
        String[] firstLine = arr[0].split(" ");
        verb = firstLine[0];
        uri = firstLine[1];
        httpVersion = firstLine[2];

        int index = 1;
        headers = new HashMap<>();
        while (arr[index].contains(": ")) { // or while != " " ?
            String[] headersArr = arr[index].split(": ");
            headers.put(headersArr[0], headersArr[1]);
            index++;
        }

        System.out.println(headers);

        if (headers.containsKey("Content-Length")) {
            body = arr[index + 1];
        }

        System.out.println(body);
    }
    // accessors
    // get something from ConfigurationReader?


    public static void main (String args[]) {
        Request myReq = new Request("GET /docs/index.html HTTP/1.1\n" +
                                    "Host: www.nowhere123.com\n" +
                                    "Content-Length: 11\n" +
                                    "    \n" +
                                    "Hello World");

        System.out.println("head" + myReq.headers);
    }
}
