package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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

    @Override
    public String getDefaultHeader(){
        return ("Date: " + new Date() +
                "\nServer: " + getServerName() +
                "\nContent-Type: " + resource.getContentType() +
                "\nContent-Length: " +  resource.getBody().length +
                "\nLast-Modified: " + resource.getLastModified());
    }

}
