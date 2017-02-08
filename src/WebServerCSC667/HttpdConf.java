package WebServerCSC667;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HttpdConf extends ConfigurationReader{
    //TODO:
    //CREATE HASH MAP FOR EACH VARIABLE
    private HashMap <String, String> aliases = new HashMap<>();
    private HashMap <String, String> scriptAliases = new HashMap<>();
    String[] temp;
    private HashMap <String, String> httpdConfig = new HashMap<>();

    // etc.

    public HttpdConf(String fileName) {
        file = new File (fileName);
        load();

        do{
            temp = nextLine().split(" ");
                if (temp[0].equals("Alias")){
                    aliases.put(temp[1],temp[2]);
                }
                else if (temp[0].equals ("ScriptAlias")) {
                    scriptAliases.put(temp[1],temp[2]);
                }
                else{
                    httpdConfig.put(temp[0], temp[1]);
                }
        }while(hasMoreLines() == true);


        /*
        for (int index = 0; index < parts.length; index++){
            temp = parts[index].split(" ");

            if (temp[0].equals("Alias")){
                aliases.put(temp[1],temp[2]);
            }
            else if (temp[0].equals ("ScriptAlias")) {
                scriptAliases.put(temp[1],temp[2]);
            }
            else{
                httpdConfig.put(temp[0], temp[1]);
            }
        }
        */
    }
/*
    public void load() {
        try {
             fileContents = new String(Files.readAllBytes(Paths.get(fileLocation)));
        }
        catch (java.lang.Exception ee){
            System.out.println("Error: Failed to load file");
        }
    }
*/

    public static void main(String args[]) {
        HttpdConf test = new HttpdConf("httpd.conf");
        System.out.println(test.parsedFile);
        //System.out.println(test.parts[1]);
        System.out.println("ALIASES\n" + test.aliases);
        System.out.println("SCRIPT\n" + test.scriptAliases);
        System.out.println(test.httpdConfig);
    }
}
