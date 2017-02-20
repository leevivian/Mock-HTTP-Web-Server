package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.util.Date;

/**
 * Created by rain2 on 2/19/2017.
 */
public class HeadResponse extends Response {
    public HeadResponse (Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(false);
    }

    public String getDefaultHeader(){
        return ("Date: " + new Date() +
                "\nServer: CSC 667 Sailor Scouts" +
                "\nContent-Type: " + resource.getContentType() +
                "\nLast-Modified: " + resource.getLastModified());
    }
}
