package WebServerCSC667;

import java.util.Dictionary;

/**
 * Created by Vivian on 2/3/17.
 */
public class MimeTypes extends ConfigurationReader{

    private Dictionary types;

    public MimeTypes(String fileName) {

    }

    public void load() {

    }

    public String lookup(String extension) {
        return "extension";
    }

}
