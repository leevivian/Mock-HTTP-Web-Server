import java.util.Dictionary;

/**
 * Created by Vivian on 2/3/17.
 */
public class Htpassword extends ConfigurationReader{

    private Dictionary users;

    public void load(){

    }

    public boolean isAuthorized(String username, String password) {
        return false;
    }
}
