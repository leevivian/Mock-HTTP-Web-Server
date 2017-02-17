package WebServerCSC667;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.stream.Stream;

public class Resource {

    private URI myURI;
    private HttpdConf myConf;
    private MimeTypes myMimeType;
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

    public Resource (String uri, HttpdConf config, MimeTypes mimeTypes) throws URISyntaxException{

        myConf = config;
        myURIString = uri;
        myMimeType = mimeTypes;
        String[] temp = uri.split("/");

        checkContainsScriptAliasKey(temp, myConf);
        checkContainsAliasKey(temp, myConf);
       // System.out.println("OUT OF FOR CONFIG TESTS: "+ this.myPath);
        //System.out.println("uri: " +  uri);
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
            this.myPath = docuRoot + myURIString;
        }
       // System.out.println("docuRoot: " + docuRoot);
        //System.out.println("myURIString: " + myURIString);
        //System.out.println("myPath: " + this.myPath);

        //TODO: File checks do not work because they check the jrob server
        //but the file Check works
        // If the path is not a file, append DirIndex

        if (new File(myPath).isFile() == true){
            //System.out.println("ITS A FILE");
            myPath = "public_html/index.html";
        }

        // Get absolute path?
        absolutePath = myPath;
        //System.out.println("ABSOLUTEPATH: " + getAbsolutePath());

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
        if ((modifiedScriptAliasURI == true) && (myConf.getScriptAliases() != null)) {
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
                this.myPath = config.getScriptAliases().get("/" + temp[i] + "/");
                System.out.println("CONFIG TESTS: " + this.myPath);
                this.modifiedScriptAliasURI = true;
                this.modifiedURI = true;

                i++;
                //Reapprend the rest of the URI
                while (i < temp.length) {
                    this.myPath = this.myPath + temp[i];
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
                this.myPath = config.getAliases().get("/"+temp[i]+"/");
                System.out.println("CONFIG TESTS: "+this.myPath);
                this.modifiedURI = true;

                i++;
                //Reapprend the rest of the URI
                while (i < temp.length){
                    this.myPath = this.myPath + temp[i];
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

    /*
    public static void main (String args[]) throws URISyntaxException{
        HttpdConf myHttpdConf = new HttpdConf("httpd.conf");
        MimeTypes myMime = new MimeTypes( "mime.types");

        // test by using script alias URI
        Resource myRes = new Resource("public_html/cgi-bin/perl_env", myHttpdConf, myMime);
        try {
            decodeMe = URLDecoder.decode(myRes.myURI.toString(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(decodeMe);
        System.out.println(myRes.toString());

        Stream.Builder b = Stream.builder();
        b.accept("POST / HTTP/1.1\n" +
                "cache-control: no-cache\n" +
                "Postman-Token: 69936131-e3f1-4e70-9a2f-dbb51d33c814\n" +
                "User-Agent: PostmanRuntime/3.0.9\n" +
                "Accept: hkhjh\n" +
                "Host: localhost:8096\n" +
                "accept-encoding: gzip, deflate\n" +
                "content-length: 0\n" +
                "Connection: keep-alive");

        Stream<String> s = b.build();

        Request myReq = new Request(s);

        Response test = ResponseFactory.getResponse(myReq, myRes);
        System.out.println(myReq.getHeaders());
    }
    */

}
