package WebServerCSC667.response;

import WebServerCSC667.Htaccess;
import WebServerCSC667.Htpassword;
import WebServerCSC667.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Created by rain2 on 2/19/2017.
 */
public class UnauthorizedResponse extends Response {

    private Htaccess htaccess;

    public UnauthorizedResponse(Resource resource, Htaccess htaccess){
        super(resource, 401);
        reasonPhrase = "Unauthorized";
        setSendBody(true);
        this.htaccess = htaccess;
    }

    // WWW-Authenticate: Basic realm="User Visible Realm"
    @Override
    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);

        ps.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        ps.println("Date: " + new Date());
        ps.println("Server: CSC 667 Sailor Scouts");
        ps.println("Content-Type: " + resource.getContentType());
        ps.println("Content-Length: " + resource.getBody().length);
        ps.println("WWW-Authenticate: " + htaccess.getAuthType() + " realm=" + htaccess.getAuthName());
        //ps.println("WWW-Authenticate: Basic realm=\"User Visible Realm");
        ps.println();
        ps.println(resource.getBody().toString());

        ps.flush();
        ps.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
