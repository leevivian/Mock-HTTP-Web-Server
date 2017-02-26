package WebServerCSC667.response;

import WebServerCSC667.configuration.Htaccess;
import WebServerCSC667.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

public class UnauthorizedResponse extends Response {

    private Htaccess htaccess;

    public UnauthorizedResponse(Resource resource, Htaccess htaccess){
        super(resource, 401);
        reasonPhrase = "Unauthorized";
        setSendBody(true);
        this.htaccess = htaccess;
    }

    @Override
    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);

        ps.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        ps.println("Date: " + new Date());
        ps.println("Server: CSC 667 Sailor Scouts");
        ps.println("Content-Type: " + resource.getContentType());
        ps.println("Content-Length: " + resource.getBody().length);
        ps.println("WWW-Authenticate: " + htaccess.getAuthType() + " realm=" + htaccess.getAuthName());
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
