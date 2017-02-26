package WebServerCSC667.configuration;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by rain2 on 2/7/2017.
 */
public class ConfigurationReader {

    private File file;
    String fileContents;
    private String[] parsedFile;
    private int parsedFileIndex = 0;

    public void setFile(File file) {
        this.file = file;
    }
    public void setParsedFile(String[] parsedFile) {
        this.parsedFile = parsedFile;
    }
    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }
    public File getFile() {
        return file;
    }
    public String getFileContents() {
        return fileContents;
    }

    public ConfigurationReader() {
    }
    public ConfigurationReader(String filename) {
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
            String newLine = parsedFile[parsedFileIndex];
            parsedFileIndex++;
            return newLine;
        }
        return null;
    }

    public void load() {
        try {
            fileContents = new String(Files.readAllBytes(Paths.get("src/conf/"+file)));
            parse(fileContents);
        } catch (IOException e) {
            System.out.println("failed to read file");
            e.printStackTrace();
        }
    }

    public void parse(String fileContents){
        parsedFile = fileContents.split("\n");
    }

}