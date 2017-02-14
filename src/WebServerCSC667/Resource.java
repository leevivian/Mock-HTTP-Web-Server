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
    private boolean modifiedURI = false;
    private boolean modifiedScriptAliasURI = false;

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

        checkContainsScriptAliasKey(temp, config);
        checkContainsAliasKey(temp, config);
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
        if (modifiedURI == false) {
            myPath = docuRoot + myURIString;
        }
        System.out.println("docuRoot: " + docuRoot);
        System.out.println("myURIString: " + myURIString);
        System.out.println("myPath: " + myPath);

        //TODO: File checks do not work because they check the jrob server
        //but the file Check works
        // If the path is not a file, append DirIndex

        if (new File(myPath).isFile() == true){
            //System.out.println("ITS A FILE");
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
        if ((modifiedScriptAliasURI == true) && myConf.getScriptAliases() != null) {
            return true;
        }
        return false;
    }


    // If a path is protected, authentification process should occur
    public boolean isProtected(){
        if (new File(myPath+ "/.htaccess").exists() == true) {
            //System.out.println("ITS PROTECTED");
            return true;
        }
        return false;
    }

    //Check if uri has a scriptAlias
    //Starts @ the end od temp[] index
    //TODO: Remove ghetto parse "/"
    public void checkContainsScriptAliasKey(String[] temp, HttpdConf config) {
        for (int i = temp.length-1; i > 0; i--) {

            if (config.getScriptAliases().containsKey("/" + temp[i] + "/")) {
                System.out.println("KEY TESTS");
                myPath = config.getScriptAliases().get("/" + temp[i] + "/");
                System.out.println("CONFIG TESTS: " + myPath);
                this.modifiedScriptAliasURI = true;
                this.modifiedURI = true;

                i++;
                //Reapprend the rest of the URI
                while (i < temp.length) {
                    myPath = myPath + temp[i];
                    i++;
                }
                break;
            }
        }
    }
    public void checkContainsAliasKey(String[] temp, HttpdConf config){
        for (int i = temp.length-1; i > 0; i--) {
            if (config.getAliases().containsKey("/"+temp[i]+"/")) {
                System.out.println("KEY TESTS");
                myPath = config.getAliases().get("/"+temp[i]+"/");
                System.out.println("CONFIG TESTS: "+myPath);
                this.modifiedURI = true;

                i++;
                //Reapprend the rest of the URI
                while (i < temp.length){
                    myPath = myPath + temp[i];
                    i++;
                }
                break;
            }
        }
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
        Resource myRes = new Resource("public_html/ab1/ab2/index.html", myHttpdConf);
        try {
            decodeMe = URLDecoder.decode(myRes.myURI.toString(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(decodeMe);
        System.out.println(myRes.toString());
    }
}
