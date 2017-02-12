package WebServerCSC667;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Stream;

public class Request {
    private String uri;
    private String body;
    private String verb;
    private String httpVersion;
    private HashMap headers;

    private Object myStr;

    public Request (String test){
        myStr = test;
        parse();
    }
    public Request (Stream client) {
        String thisLine;
        String streamToString = client.iterator().next().toString();
        StringReader readString = new StringReader(streamToString);
        BufferedReader readText = new BufferedReader(readString);

        try {
            while ((thisLine = readText.readLine()) != null) {
                myStr = myStr + thisLine + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        parse();
    }
    public void parse() {
        boolean contentLength = false;
        String[] arr = (myStr.toString()).split("[\n\r]+");

        // HTTP_METHOD IDENTIFIER HTTP_VERSION
        String[] firstLine = arr[0].split(" ");
        verb = firstLine[0];
        uri = firstLine[1];
        httpVersion = firstLine[2];

        int index = 1;
        headers = new HashMap();
        while (arr[index].contains(": ") && index < arr.length - 1) { // or while != " " ?
            String[] headersArr = arr[index].split(": ");
            headers.put(headersArr[0], headersArr[1]);
            index++;
        }


        if (headers.containsKey("Content-Length")) {
            body = arr[index + 1];
        }

    }

    public void printMe() {
        // For testing purposes
        System.out.println("-----THIS IS A TEST from the Request class-----");
        System.out.println("Verb:" + verb);
        System.out.println("URI:" + uri);
        System.out.println("Header:" + headers);
        System.out.println("Body:" + body);
        System.out.println("-----End of test!-----");
    }

    // accessors

    // for testing
    public static void main (String args[]){
        String test = "POST / HTTP/1.1\n" +
                "cache-control: no-cache\n" +
                "Postman-Token: 69936131-e3f1-4e70-9a2f-dbb51d33c814\n" +
                "User-Agent: PostmanRuntime/3.0.9\n" +
                "Accept: */*\n" +
                "Host: localhost:8096\n" +
                "accept-encoding: gzip, deflate\n" +
                "content-length: 0\n" +
                "Connection: keep-alive";

        Request myReq = new Request(Stream.of(test));

    }

}