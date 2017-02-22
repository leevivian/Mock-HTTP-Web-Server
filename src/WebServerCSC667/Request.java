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

    public boolean flagBR = false;

    private String myStr = "";

    public Request (String test){
        myStr = test;
    }

    public Request (Stream client) {
        Iterator it =  client.iterator();

        while (it.hasNext()) {
            myStr += it.next().toString();
        }
    }

    public void parse(){
        boolean contentLength = false;
        String[] arr = (myStr.toString()).split("[\n\r]+");

        // HTTP_METHOD IDENTIFIER HTTP_VERSION
        String[] firstLine = arr[0].split("\\s+");

        if (!firstLine[0].matches("GET|POST|PUT|DELETE|HEAD")) {
            System.out.println("1");
        }



        if (firstLine.length < 3 || !firstLine[0].matches("GET|POST|PUT|DELETE|HEAD")) {
            flagBR = true;
        } else {

            verb = firstLine[0];
            uri = firstLine[1];
            httpVersion = firstLine[2];

            // TODO: If headers don't exist...

            int index = 1;
            headers = new HashMap();
            while (arr[index].contains(": ") && index < arr.length - 1) { // or while != " " ?
                String[] headersArr = arr[index].split(": ");
                headers.put(headersArr[0], headersArr[1]);
                index++;
            }

            // What if content-length is 0?
            if (headers.containsKey("Content-Length") || headers.containsKey("content-length")) {
                body = arr[index];
            }
        }

    }

    public String getVerb() {
        return verb;
    }

    public String getURI(){
        return uri;
    }

   public String getHttpVersion() {
        return httpVersion;
   }

   public HashMap getHeaders() {
        return headers;
   }

   public String getBody() {
        return body;
   }

    public String getAuthHeader() {
        if (headers.containsKey("Authorization")) {
            return headers.get("Authorization").toString();
        } else {
            return null;
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

        Stream.Builder b = Stream.builder();
        b.accept("POST / HTTP/1.1\n" +
                "cache-control: no-cache\n" +
                "Postman-Token: 69936131-e3f1-4e70-9a2f-dbb51d33c814\n" +
                "User-Agent: PostmanRuntime/3.0.9\n" +
                "Accept: */*\n" +
                "Host: localhost:8096\n" +
                "accept-encoding: gzip, deflate\n" +
                "content-length: 0\n" +
                "Connection: keep-alive");

        Stream<String> s = b.build();

        Request myReq = new Request(s);
        myReq.printMe();

    }

}