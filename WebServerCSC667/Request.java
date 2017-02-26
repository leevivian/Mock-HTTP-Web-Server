package WebServerCSC667;

import java.util.*;
import java.util.stream.Stream;

public class Request {
    private String uri;
    private String body;
    private String verb;
    private String httpVersion;
    private HashMap headers;

    public boolean flagRequestParsingException = false;

    private String requestString = "";

    public Request (String test){
        requestString = test;
    }

    public Request (Stream client) {
        Iterator iterator =  client.iterator();

        while (iterator.hasNext()) {
            requestString += iterator.next().toString();
        }
    }

    public void parseRequestString(){
        String[] requestLines = (requestString.toString()).split("[\n\r]+");

        String[] requestFirstLine = requestLines[0].split("\\s+");

        if (requestFirstLine.length < 3 || !requestFirstLine[0].matches("GET|POST|PUT|DELETE|HEAD")) {
            flagRequestParsingException = true;
        } else {

            verb = requestFirstLine[0];
            uri = requestFirstLine[1];
            httpVersion = requestFirstLine[2];
            headers = new HashMap();

            if (requestLines.length > 2) {
                int index = 1;

                while (requestLines[index].contains(": ") && index < requestLines.length - 1) {
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
}