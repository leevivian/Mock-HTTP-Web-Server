package WebServerCSC667.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Htaccess extends ConfigurationReader {

    private Htpassword userFile;
    private String authType;
    private String authName;
    private String require;

    public Htaccess(String htaccessLocation) {
        this.load(htaccessLocation);
    }

    public void load(String htaccessLocation) {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get(htaccessLocation)));
            parse(fileContents);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            e.printStackTrace();
        }
    }

    public void parse(String fileContents) {
        String[] tempLineSplit = fileContents.split("\n");
        for (int index = 0; index < tempLineSplit.length; index++) {
            String[] temp = tempLineSplit[index].replaceAll("\"", "").split(" ", 2);
            switch (temp[0]) {
                case "AuthUserFile":
                    try {
                        userFile = new Htpassword(temp[1]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "AuthType":
                    authType = temp [1];
                    break;
                case "AuthName":
                    authName = temp[1];
                    break;
                case "Require":
                    require = temp[1];
                    break;
                default:
                    break;
            }
        }
    }
    public boolean isAuthorized(String username, String password) {
        if (userFile.isAuthorized(new String (username + ":" + password)) == true){
            return true;
        }
        return false;
    }

    public Htpassword getHtpassword() {
        return userFile;
    }
    public String getAuthType() {
        return authType;
    }
    public String getAuthName() {
        return authName;
    }

}
