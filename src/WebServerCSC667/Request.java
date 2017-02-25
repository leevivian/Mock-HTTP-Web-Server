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
        String[] requestLines = (myStr.toString()).split("[\n\r]+");

        String[] requestFirstLine = requestLines[0].split("\\s+");

        if (requestFirstLine.length < 3 || !requestFirstLine[0].matches("GET|POST|PUT|DELETE|HEAD")) {
            flagBR = true;
        } else {

            verb = requestFirstLine[0];
            uri = requestFirstLine[1];
            httpVersion = requestFirstLine[2];
            headers = new HashMap();

            if (requestLines.length > 2) {
                int index = 1;

                while (requestLines[index].contains(": ") && index < requestLines.length - 1) { // or while != " " ?
                    String[] headersArray = requestLines[index].split(": ");
                    headers.put(headersArray[0], headersArray[1]);
                    index++;
                }

                if (headers.containsKey("Content-Length") || headers.containsKey("content-length")) {
                    body = requestLines[index];
                }
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

    public String getEtag(){
        if (headers.containsKey("Etag")){
            return headers.get("Etag").toString();
        }
        else{
            return null;
        }
    }

    public String getCacheControl(){
        if (headers.containsKey("Cache-Control")){
            return headers.get("Cache-Control").toString();
        }
        else{
            return null;
        }
    }

    public String getIPAddress() {
        if (headers.containsKey("X-FORWARDED-FOR")) {
            return headers.get("X-FORWARDED-FOR").toString();
        } else if (headers.containsKey("x-forwarded-for")) {
            return headers.get("x-forwarded-for").toString();
        } else if (headers.containsKey("X-Forwarded-For")) {
            return (headers.get("X-Forwarded-For")).toString();
        }
        return "-";
    }

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

    }

}