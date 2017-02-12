package WebServerCSC667;

import java.io.File;
import java.util.HashMap;

public class HttpdConf extends ConfigurationReader{

    private HashMap <String, String> aliases = new HashMap<>();
    private HashMap <String, String> scriptAliases = new HashMap<>();
    private HashMap <String, String> httpdConfig = new HashMap<>();
    private String documentRoot = "";
    private String serverRoot = "";
    private String logFileLocaction = "";
    private int port = 80;

    public int getPort() {
        return port;
    }
    public String getServerRoot() {
        return serverRoot;
    }
    public String getLogFileLocaction() {
        return logFileLocaction;
    }
    public String getDocumentRoot() {
        return documentRoot;
    }
    public HashMap<String, String> getAliases() {
        return aliases;
    }
    public HashMap<String, String> getScriptAliases() {
        return scriptAliases;
    }
    public HashMap<String, String> getHttpdConfig() {
        return httpdConfig;
    }

    public HttpdConf(String fileName) {
        setFile (new File (fileName));
        load();
    }

    public void load() {
        super.load();
        parse();
    }

    public void parse(){
        String[] temp;
        while(hasMoreLines() == true){
            temp = nextLine().split(" ");
            //System.out.println(temp[0]);
            switch (temp[0]) {
                case "DocumentRoot":
                    documentRoot = temp[1];
                    break;
                case "Alias":
                    aliases.put(temp[1],temp[2]);
                    break;
                case "ScriptAlias":
                    scriptAliases.put(temp[1],temp[2]);
                    break;
                case "Listen":
                    port = Integer.parseInt(temp[1]);
                    break;
                case "LogFile":
                    logFileLocaction = temp[1];
                    break;
                case "ServerRoot":
                    serverRoot = temp[1];
                    break;
                default:
                    httpdConfig.put(temp[0], temp[1]);
                    break;
            }
        }
    }

    @Override
    public String toString() {
        return "HttpdConf{" +
                "\naliases=" + aliases +
                ", \nscriptAliases=" + scriptAliases +
                ", \nhttpdConfig=" + httpdConfig +
                ", \ndocumentRoot='" + documentRoot + '\'' +
                ", \nserverRoot='" + serverRoot + '\'' +
                ", \nlogFileLocaction='" + logFileLocaction + '\'' +
                ", \nport=" + port +
                '}';
    }
    public static void main(String args[]) {
        HttpdConf test = new HttpdConf("httpd.conf");
        //System.out.println(test.parts[1]);
        //System.out.println("ALIASES:  " + test.aliases);
        //System.out.println("SCRIPT:  " + test.scriptAliases);
        //System.out.println("port:" + test.port);
        System.out.println(test.toString());
    }
}
