package WebServerCSC667;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Resource {

    private File myFile;
    private InputStream is;

    public Resource (String uri, HttpdConf config){
        try {
            // Construct a URI by parsing the given string, uri
            URI myURI = new URI(uri);
            // Create new file instance by converting the given file: URI into an abstract pathname
            myFile = new File (myURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            // Return class object with the class with the given string name
            Class myClass = Class.forName("Resource");
            // get class loader
            ClassLoader classLoader = myClass.getClassLoader();
            // Finds resource with the given name as an InputStream
            is = classLoader.getResourceAsStream(absolutePath());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public String absolutePath(){
        return myFile.getAbsolutePath();
    }
    public boolean isScript(){
        // if Script-Alias exists, then return true
        return false;
    }

    public boolean isProtected(){
        return false;
    }
}
