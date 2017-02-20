package WebServerCSC667;

import java.awt.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Resource {

    private URI myURI;
    private HttpdConf myConf;
    private MimeTypes myMimeType;
    private String alias;
    private String myURIString;
    private String docuRoot;
    private String absolutePath;
    private String contentType;
    private File file;
    private String myPath = "";
    private boolean modifiedScriptAliasURI = false;
    private boolean modifiedURI = false;

    byte[] body = "".getBytes();

    public void setBody(byte[] body) {
        this.body = body;
    }
    public byte[] getBody() {
        return body;
    }
    public String getContentType() {
        return contentType;
    }
    public String getAbsolutePath(){
        return absolutePath;
    }
    public boolean isModifiedScriptAliasURI() {
        return modifiedScriptAliasURI;
    }
    public boolean isModifiedURI() {
        return modifiedURI;
    }

    // V
    HashMap reqheaders;

    public void setHeaders(HashMap headers) {
        reqheaders = headers;
    }
    public HashMap getHeaders() {
        return reqheaders;
    }
    // V

    // For test
    static String decodeMe;

    public Resource (String uri, HttpdConf config, MimeTypes mimeTypes) throws URISyntaxException{

        myConf = config;
        myURIString = uri;
        myMimeType = mimeTypes;

        checkContainsScriptAliasKey(uri, myConf);
        checkContainsAliasKey(uri, myConf);
        contentType = setContentType(uri, myMimeType);

        // ******* RESOLVE PATH *******
        docuRoot = config.getDocumentRoot();
        if (modifiedURI == false) {
            this.myPath = docuRoot + myURIString;
        }

        System.out.println("docuRoot: " + docuRoot);
        System.out.println("myURIString: " + myURIString);
        System.out.println("myPath: " + this.myPath);

        absolutePath = myPath;

        // TODO: Append DirIndex, do not hardcode index.html
        if (new File(getAbsolutePath()).isFile() == false){
            absolutePath = absolutePath + "index.html";
        } // else absolutePath is fine just the way it is

        try {
            // needs encoding otherwise - URISyntaxException: Illegal character in path
            myURI = new URI(URLEncoder.encode(myPath, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

    public String setContentType(String uri, MimeTypes mimeTypes){
        //System.out.println("URI: " +uri);
        String[] temp = uri.split("/");

        //System.out.println("LENGTH" + temp.length);
        if (temp.length < 1){
            return mimeTypes.lookup("html");
        }
        else if (temp.length > 1) {
            String[] mimeExtension = (temp[temp.length - 1]).split("\\.");

            if (mimeExtension.length > 1) {
                System.out.println("CONTENT TYHPE CHECK: " + contentType);
                return mimeTypes.lookup(mimeExtension[mimeExtension.length - 1]);
            }
        }
        return null;
    }
    //Check if uri has a scriptAlias
    //Starts @ the end od temp[] index
    //TODO: Remove ghetto parse "/"
    public void checkContainsScriptAliasKey(String uri, HttpdConf config) {
        String[] temp = uri.split("/");

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
    public void checkContainsAliasKey(String uri, HttpdConf config){
        String[] temp = uri.split("/");

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
