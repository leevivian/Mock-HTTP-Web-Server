package WebServerCSC667;

import WebServerCSC667.configuration.HttpdConf;
import WebServerCSC667.configuration.MimeTypes;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;

public class Resource {

    private HttpdConf configuration;
    private MimeTypes mimeType;
    private String uriString;
    private String docuRoot;
    private String absolutePath;
    private String contentType;
    private boolean isModifiedResource = false;
    private String resolvedPath = "";
    private Date lastModified;
    private boolean modifiedScriptAliasURI = false;
    private boolean modifiedURI = false;
    private HashMap reqheaders;
    byte[] body = "".getBytes();
    String htacessLocation = "";
    private String queryString;


    public Resource (String uri, HttpdConf config, MimeTypes mimeTypes) throws URISyntaxException{

        configuration = config;
        uriString = uri;
        mimeType = mimeTypes;

        checkContainsScriptAliasKey(uri, configuration);
        checkContainsAliasKey(uri, configuration);
        contentType = setContentType(uri, mimeType);
        docuRoot = config.getDocumentRoot();

        if (modifiedURI == false) {
            this.resolvedPath = docuRoot + uriString;
        }

        absolutePath = resolvedPath;
        setQueryString();

        if (!resolvedPath.contains(".") && !isScript()){
            absolutePath = absolutePath + "/" + config.getDirectoryIndex();
        }
    }

    public boolean isScript(){
        if ((modifiedScriptAliasURI == true) && (configuration.getScriptAliases() != null)) {
            return true;
        }
        return false;
    }

    public boolean isProtected(){

        String[] parseAbsolutePath = absolutePath.split("/");
        for (int index = 0; index < parseAbsolutePath.length; index++){
            htacessLocation += parseAbsolutePath[index] + "/";
            if (new File(htacessLocation + configuration.getAccessFileName()).exists()) {
                return true;
            }
        }
        return false;
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

    public void checkContainsScriptAliasKey(String uri, HttpdConf config) {
        String[] temp = uri.split("/");

        for (int i = temp.length-1; i > 0; i--) {
            if (config.getScriptAliases().containsKey("/" + temp[i] + "/")) {
                this.resolvedPath = config.getScriptAliases().get("/" + temp[i] + "/");
                this.modifiedScriptAliasURI = true;
                this.modifiedURI = true;
                i++;

                while (i < temp.length) {
                    this.resolvedPath = this.resolvedPath + temp[i];
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
                this.resolvedPath = config.getAliases().get("/"+temp[i]+"/");
                this.modifiedURI = true;
                i++;

                while (i < temp.length){
                    this.resolvedPath = this.resolvedPath + temp[i];
                    i++;
                }
                break;
            }
        }
    }

    public void setQueryString() {
        if (isScript()) {
            if (absolutePath.contains("?") && !absolutePath.substring(absolutePath.length() - 1).equals("?")) {
                String separateScriptAndQueryString[] = absolutePath.split("\\?+");
                absolutePath = separateScriptAndQueryString[0];
                queryString = separateScriptAndQueryString[1];
            }
        }
    }

    public void setModifiedResource(boolean modifiedResource) {
        isModifiedResource = modifiedResource;
    }
    public void setHeaders(HashMap headers) {
        reqheaders = headers;
    }
    public HashMap getHeaders() {
        return reqheaders;
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
    public String getQueryString() {
        return queryString;
    }
    public String getHtaccessPath() {
        return htacessLocation + configuration.getAccessFileName();
    }

}
