package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.*;
import java.util.Date;

public class Response {
    public int code;
    protected String reasonPhrase = "";
    Resource resource;
    String httpVersion = "1.1";

    public void setSendBody(boolean sendBody) {
        this.sendBody = sendBody;
    }
    private boolean sendBody;

/*
HTTP/1.1 404 Not Found
Date: Sun, 18 Oct 2012 10:36:20 GMT
Server: Apache/2.2.14 (Win32)
Content-Length: 230
Connection: Closed
Content-Type: text/html; charset=iso-8859-1
 */

    public Response (){
    }

    public Response (Resource resource, int code) {
        this.resource = resource;
        this.code = code;
    }

    public String getInitialHeader(){
        return ("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
    }
    public String getDefaultHeader(){
        return ("Date: " + new Date() +
                "\nServer: CSC 667 Sailor Scouts" +
                "\nContent-Type: " + resource.getContentType());
    }
    public String getResponse(){
        return ("Content-Length: " +  resource.getBody().length +
                "\nConnection: " + //keep-alive?
                "\n\n" + new String(resource.getBody()));
    }

    public Integer getContentLength(){
        return resource.getBody().length;
    }

    public String getReasonPhrase(int code){
        switch(code) {
            case 200:
                return "OK";
            case 201:
                return "Created";
            case 204:
                return "No Content";
            case 304:
                return "Not Modified";
            case 400:
                return "Bad Request";
            case 401:
                return "Unauthorized";
            case 403:
                return "Forbidden";
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            //TODO: Default??
            default:
                return "ERROR: CODE NOT VALID";
        }
    }

    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);

        // TODO: Vivian's Postman doesn't like the commented out Response...
/*
        ps.println(getInitialHeader());
        ps.println(getDefaultHeader());
        if (sendBody == true) {
            ps.println(getResponse());
        }
        */

        //TODO: Needs to be changed back, because not all the following fields are supposed to added for each Response class
        ps.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        ps.println("Date: " + new Date());
        ps.println("Server: CSC 667 Sailor Scouts");
        ps.println("Content-Type: " + resource.getContentType());
        ps.println("Content-Length: " +  resource.getBody().length);
        ps.println("Connection: ");
        ps.println();
        if (sendBody && !resource.getContentType().contains("image")) {
            ps.println(new String(resource.getBody()));
        } else if (sendBody && resource.getContentType().contains("image")) {
            try {
                out.write(resource.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ps.flush();
        ps.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
