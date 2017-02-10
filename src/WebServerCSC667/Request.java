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

    public Request (String test) throws BadRequest{
        myStr = test;
        parse();
    }
    public Request (Stream client) throws BadRequest{
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
    public void parse() throws BadRequest{
        boolean contentLength = false;
        String[] arr = (myStr.toString()).split("[\n\r]+");

        // HTTP_METHOD IDENTIFIER HTTP_VERSION
        String[] firstLine = arr[0].split(" ");
        verb = firstLine[0];
        uri = firstLine[1];
        httpVersion = firstLine[2];

        int index = 1;
        headers = new HashMap();
        while (arr[index].contains(": ")) { // or while != " " ?
            String[] headersArr = arr[index].split(": ");
            headers.put(headersArr[0], headersArr[1]);
            index++;
        }


        if (headers.containsKey("Content-Length")) {
            body = arr[index + 1];
        }

    }

    // accessors

    public static void main (String args[]) throws BadRequest{
        String test = "GET /docs/index.html HTTP/1.1\n" +
                "Host: www.nowhere123.com\n" +
                "Content-Length: 11\n" +
                "    \n" +
                "Hello World";

        Request myReq = new Request(Stream.of(test));

        System.out.println("Headers: " + myReq.headers + "\nBody: " + myReq.body);

    }
}