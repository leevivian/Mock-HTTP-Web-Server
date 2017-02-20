package WebServerCSC667;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rain2 on 2/7/2017.
 */
public class ConfigurationReader {

    public File getFile() {
        return file;
    }

    private File file;

    public void setParsedFile(String[] parsedFile) {
        this.parsedFile = parsedFile;
    }

    private String[] parsedFile;
    private int parsedFileIndex = 0;

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    String fileContents;

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

    public void setFile(File file) {
        this.file = file;
    }

    public String nextLine(){
        if (hasMoreLines() == true) {
            String temp = parsedFile[parsedFileIndex];
            parsedFileIndex++;
            //System.out.println("nextLine TRUE");
            return temp;
        }
        //System.out.println("nextLine FALSE");
        return null;
    }

    public void load() {
        try {
            fileContents = new String(Files.readAllBytes(Paths.get("./conf/"+file)));
            parse(fileContents);
        } catch (IOException e) {
            System.out.println("failed to read file");
            e.printStackTrace();
        }
    }

    public void parse(String fileContents){
        parsedFile = fileContents.split("\n");
    }

    public static void main(String args[]) {
        ConfigurationReader test = new ConfigurationReader("httpd.conf");
        System.out.println("hello");
        System.out.println(test);
        for (int i = 0; i <test.parsedFile.length; i++){
            System.out.println(test.parsedFile[i]);
        }
        //System.out.println(test.nextLine());
    }

}