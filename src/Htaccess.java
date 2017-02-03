/**
 * Created by Vivian on 2/3/17.
 */
public class Htaccess extends ConfigurationReader {

    private Htpassword userFile;
    private String authType;
    private String authName;
    private String require;

    public void load() {

    }

    public boolean isAuthorized(String username, String password) {
        return false;
    }

}
