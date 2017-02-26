package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Response {
    public int code;
    protected String reasonPhrase = "";
    Resource resource;
    String httpVersion = "1.1";
    InetAddress inetAddress;
    private boolean sendBody;

    public Response() {
    }

    public Response(Resource resource, int code) {
        this.resource = resource;
        this.code = code;
    }

    public String getInitialHeader() {
        return ("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
    }

    public String getDefaultHeader() {
        return ("Date: " + new Date() +
                "\nServer: CSC 667 Sailor Scouts" +
                "\nContent-Type: " + resource.getContentType());
    }

    public String getResponse() {
        return ("Content-Length: " + resource.getBody().length +
                "\nConnection: " + //keep-alive?
                "\n\n" + new String(resource.getBody()));
    }

    public Integer getContentLength() {
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

    public void send(OutputStream out) {
        PrintStream ps = new PrintStream(out);

        ps.println(getInitialHeader());
        ps.println(getDefaultHeader());
        Date expireDate = new Date();
        expireDate.setHours(expireDate.getHours() + 2);
        ps.println("Expires: " + expireDate);
        if (sendBody == true) {
            ps.println("Content-Length: " + resource.getBody().length);
            ps.println();
        }
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
