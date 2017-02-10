package WebServerCSC667;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Resource {

    private URI myURI;
    private HttpdConf myConf;
    private String alias;
    private String myURIString;
    private String docuRoot;

    private File file;
    private String myPath;

    // For test
    static String decodeMe;

    public Resource (String uri, HttpdConf config) throws URISyntaxException{

        myConf = config;
        myURIString = uri;

        // check if there are aliases
        if ((alias = config.getAliases().get(uri)) != null) {
                myURIString = alias;
        }
         else if (isScript()) {   // also check if ScriptAlias exists

            myURIString = config.getScriptAliases().get(uri);
        }

        // ******* RESOLVE PATH *******
        docuRoot = config.getDocumentRoot();
        myPath = docuRoot + myURIString;

        // If the path is not a file, append DirIndex
        file = new File(myPath);
        if (!file.isFile()) {
            myPath += "index.html";
        }

        // Get absolute path?
        myPath = absolutePath();

        try {
            // needs encoding otherwise - URISyntaxException: Illegal character in path
            myURI = new URI(URLEncoder.encode(myPath, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String absolutePath(){
        return file.getAbsolutePath();
    }

    public boolean isScript(){

        if (myConf.getScriptAliases() != null) {
            return true;
        }
        return false;
    }

    // If a path is protected, authentification process should occur
    public boolean isProtected(){
        if (myPath.contains("protected")) {
            return true;
        }
        return false;
    }

    public static void main (String args[]) throws URISyntaxException{
        HttpdConf myHttpdConf = new HttpdConf("httpd.conf");

        // test by using script alias URI
        Resource myRes = new Resource("/cgi-bin/", myHttpdConf);
        try {
            decodeMe = URLDecoder.decode(myRes.myURI.toString(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(decodeMe);
    }
}
