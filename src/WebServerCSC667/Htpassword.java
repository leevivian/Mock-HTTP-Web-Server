package WebServerCSC667;

import java.util.HashMap;

/**
 * Created by Vivian on 2/3/17.
 */
public class Htpassword {

    private HashMap<String, String> users;

    public void load(){

    }
    /*
    jrob:{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=
    job = user
    {SHA} = encription
    cRDtpNCeBiql5KOQsKVyrA0sAiA= = password?
     */
    public boolean isAuthorized(String username, String password) {
        return false;
    }
}
