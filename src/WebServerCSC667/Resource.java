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
    private String absolutePath;

    private File file;
    private String myPath;
    private boolean ModifiedURI = false;

    public int getResponseCode() {
        return responseCode;
    }

    private int responseCode;

    // For test
    static String decodeMe;

    public Resource (String uri, HttpdConf config) throws URISyntaxException{

        myConf = config;
        myURIString = uri;
        String[] temp = uri.split("/");
        System.out.println(temp[0]);
        System.out.println(temp[1]);

        //TODO: remove ghetto parse "/"
        //Check if uri has a scriptAlias
        for (int i = 0; i < temp.length; i++) {
            if (config.getScriptAliases().containsKey("/"+temp[i]+"/")) {
                System.out.println("KEY TESTS");
                myPath = config.getScriptAliases().get("/"+temp[i]+"/");
                System.out.println("CONFIG TESTS: "+myPath);
                ModifiedURI = true;
                i++;

                //Reapprend the rest of the URI
                while (i < temp.length){
                    myPath = myPath + "/"+ temp[i];
                    i++;
                }
                break;
            }
        }
        System.out.println("OUT OF FOR CONFIG TESTS: "+myPath);
        System.out.println("uri: " +  uri);
/*
        if (0 == 0) {
            responseCode = 200;
            new Response(this);
            return;
        }
*/
        // ******* RESOLVE PATH *******
        docuRoot = config.getDocumentRoot();
        if (ModifiedURI == false) {
            myPath = docuRoot + myURIString;

        }
        System.out.println("docuRoot: " + docuRoot);
        System.out.println("myURIString: " + myURIString);
        System.out.println("myPath: " + myPath);

        // If the path is not a file, append DirIndex
        file = new File(myPath);
        if (!file.isFile()) {
            System.out.println("*****NOT A FILE***");
            myPath = "public_html/index.html";
        }

        // Get absolute path?
        absolutePath = myPath;
        System.out.println("ABSOLUTEPATH: " + getAbsolutePath());

        try {
            // needs encoding otherwise - URISyntaxException: Illegal character in path
            myURI = new URI(URLEncoder.encode(myPath, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public String getAbsolutePath(){
        return absolutePath;
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


    @Override
    public String toString() {
        return "Resource{" +
                "\nmyURI=" + myURI +
                ", \nmyConf=" + myConf +
                ", \nalias='" + alias + '\'' +
                ", \nmyURIString='" + myURIString + '\'' +
                ", \ndocuRoot='" + docuRoot + '\'' +
                ", \nfile=" + file +
                ", myPath='" + myPath + '\'' +
                '}';
    }

    public static void main (String args[]) throws URISyntaxException{
        HttpdConf myHttpdConf = new HttpdConf("httpd.conf");

        // test by using script alias URI
        Resource myRes = new Resource("public_html/cgi-bin/perl_env", myHttpdConf);
        try {
            decodeMe = URLDecoder.decode(myRes.myURI.toString(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(decodeMe);
        System.out.println(myRes.toString());
    }
}
