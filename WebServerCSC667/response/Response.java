package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Response {
    public int code;
    protected String reasonPhrase = "";
    private boolean sendBody;
    Resource resource;
    String httpVersion = "1.1";
    InetAddress inetAddress;

    public Response (){
    }

    public Response (Resource resource, int code) {
        this.resource = resource;
        this.code = code;
    }

    public Integer getContentLength(){
        return resource.getBody().length;
    }

    public String getServerName() {
        try {
            return inetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Apache";
    }

    public void setSendBody(boolean sendBody) {
        this.sendBody = sendBody;
    }

    public void send(OutputStream out){
        PrintStream printStream = new PrintStream(out);

        printStream.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        printStream.println("Date: " + new Date());
        printStream.println("Server: " + getServerName());
        printStream.println("Content-Type: " + resource.getContentType());
        printStream.println("Content-Length: " +  resource.getBody().length);
        printStream.println();

        if (sendBody && !resource.getContentType().contains("image")) {
            printStream.println(new String(resource.getBody()));
        } else if (sendBody && resource.getContentType().contains("image")) {
            try {
                out.write(resource.getBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        printStream.flush();
        printStream.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
