package WebServerCSC667;

import WebServerCSC667.configuration.HttpdConf;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

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
    private Date lastModified;
    private boolean modifiedScriptAliasURI = false;
    private boolean modifiedURI = false;
    private HashMap reqheaders;
    byte[] body = "".getBytes();
    String htacessLocation = "";
    private String queryString;

    public void setHeaders(HashMap headers) {
        reqheaders = headers;
    }
    public HashMap getHeaders() {
        return reqheaders;
    }

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

        System.out.println("Content-type: " + contentType);
        System.out.println("myURIString: " + myURIString);
        System.out.println("myPath: " + this.myPath);

        absolutePath = myPath;

        setQueryString();

        if (!myPath.contains(".") && !isScript()){
            absolutePath = absolutePath + "/" + config.getDirectoryIndex();
        } // else absolutePath is fine just the way it is

        System.out.println("Absolute Path: " + absolutePath);

        try {
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

    public void setQueryString() {
        if (isScript()) {
            if (absolutePath.contains("?") && !absolutePath.substring(absolutePath.length() - 1).equals("?")) {
                String separateScriptAndQueryString[] = absolutePath.split("\\?+");
                absolutePath = separateScriptAndQueryString[0];
                queryString = separateScriptAndQueryString[1];

                System.out.println("QueryString" + queryString);
            }
        }
    }

    public String getQueryString() {
        return queryString;
    }


    public boolean isProtected(){

        String[] parseAbsolutePath = absolutePath.split("/");
        for (int index = 0; index < parseAbsolutePath.length; index++){
            htacessLocation += parseAbsolutePath[index] + "/";
            if (new File(htacessLocation + myConf.getAccessFileName()).exists()) {
                return true;
            }
        }
        return false;
    }

    public String getHtaccessPath() {
        return htacessLocation + myConf.getAccessFileName();
    }

    public String setContentType(String uri, MimeTypes mimeTypes){

        String[] temp = uri.split("/");

        if (temp.length < 1){
            return mimeTypes.lookup("html");
        }
        else if (temp.length > 1) {
            String[] mimeExtension = (temp[temp.length - 1]).split("\\.");

            if (mimeExtension.length > 1) {
                return mimeTypes.lookup(mimeExtension[mimeExtension.length - 1]);
            }
        }
        return "text/text";
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

    public Date getLastModified() {
        return lastModified;
    }
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
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

    // TODO: Is this needed?
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
}
