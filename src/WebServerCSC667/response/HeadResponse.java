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
        reasonPhrase = "";
        setSendBody(false);
    }
    @Override
    public String getDefaultHeader(){
        return ("Date: " + new Date() +
                "\nServer: CSC 667 Sailor Scouts" +
                "\nContent-Type: " + resource.getContentType() +
                //TODO: cache-control is hard coded
                "\nCache-Control: private, max-age 86400 " +
                //EntityTag etag = new EntityTag(Integer.toString(myBook.hashCode()));
                "\nLast-Modified: " + resource.getLastModified());
    }

    @Override
    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);

        ps.println(getInitialHeader());
        ps.println(getDefaultHeader());

        ps.flush();
        ps.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
