package WebServerCSC667;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rain2 on 2/7/2017.
 */
public class ConfigurationReader {
    File file;
    String[] parsedFile;
    private int parsedFileIndex = 0;

    public ConfigurationReader() {
    }
    public ConfigurationReader(String filename) {
        //Creates a new File instance by converting the given pathname string into an abstract pathname.
        file = new File(filename);
        load();
    }

    public boolean hasMoreLines() {
        if (parsedFile.length  > parsedFileIndex){
            return true;
        }
        else {
            return false;
        }
    }

    public String nextLine(){
        if (hasMoreLines() == true) {
            String temp = parsedFile[parsedFileIndex];
            parsedFileIndex++;
            System.out.println("nextLine TRUE");
            return temp;
        }
        System.out.println("nextLine FALSE");
        return null;
    }

    public void load() {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get("conf/"+file)));
            parsedFile = fileContents.split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String args[]) {
        ConfigurationReader test = new ConfigurationReader("httpd.conf");
        System.out.println("hello");
        System.out.println(test);
        System.out.println(test.parsedFile[5]);
        //System.out.println(test.nextLine());
    }

}