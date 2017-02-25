package WebServerCSC667;

import WebServerCSC667.configuration.ConfigurationReader;

import java.lang.*;
import java.io.File;
import java.util.HashMap;

public class MimeTypes extends ConfigurationReader {

    public HashMap<String, String> getTypes() {
        return types;
    }

    private HashMap<String, String> types = new HashMap<>();;

    public MimeTypes(String fileName) {
        setFile (new File (fileName));
        load();
    }

    public void load() {
        super.load();
        parse();
    }

    public void parse(){
        while(hasMoreLines() == true){
            String[] temp = nextLine().replaceAll("\\s+", " ").split(" ");

            if ((temp.length >= 2) && (temp[0].startsWith("#") == false)) {
                for (int index = 1; index < temp.length; index++){
                    types.put(temp[index], temp[0]);
                }
            }
        }
    }
    //TODO:: Appropriate mime type returned based on file extension (defaults to text/text if not found in mime.types)
    public String lookup(String extension) {
        if(this.types.get(extension) == null){
            return "text/text";
        }
        return this.types.get(extension);
    }

    public static void main(String args[]){
        MimeTypes test = new MimeTypes( "mime.types");
        System.out.println("TEST TYPES: " + test.types);
        System.out.println("EXT TEST" + test.lookup("hobo"));

    }
}
