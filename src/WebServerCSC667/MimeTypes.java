package WebServerCSC667;

import java.lang.*;
import java.io.File;
import java.util.HashMap;

public class MimeTypes extends ConfigurationReader{

    private HashMap<String, String> types = new HashMap<>();;
    String[] temp;

    public MimeTypes(String fileName) {
        setFile (new File (fileName));
        load();

        do{
            temp = nextLine().split(" ");
            if (temp[0].contains("#")){
                System.out.println("testttt ");

            }
            else if (temp.length <= 1){
                System.out.println("skipped LIne ");

            }
            else if (temp.length >= 2) {
                System.out.println("TYPES ADD");
                System.out.println("temp 0 " + temp[0]);
                System.out.println("temp 1 " + temp[1]);
                for (int index = 1; index < temp.length; index++){
                    types.put(temp[index], temp[0]);
                }
            }
        }while(hasMoreLines() == true);

    }

    public static void main(String args[]){
        MimeTypes test = new MimeTypes( "mime.types");
        System.out.println("TEST TYPES: " + test.types);
        System.out.println("EXT TEST" + test.lookup("aiff"));
    }
/*
    public void load() {
        try {
            fileContents = new String (Files.readAllBytes(Paths.get(loadFile)));
        }
        catch (java.lang.Exception ee){
            System.out.println("Error: Failed to load file");
        }
    }
*/

    public String lookup(String extension) {
        return this.types.get(extension);
    }

}
