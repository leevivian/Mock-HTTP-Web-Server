package WebServerCSC667;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Vivian on 2/3/17.
 */
public class Htaccess extends ConfigurationReader {

    private Htpassword userFile;
    private String authType;
    private String authName;
    private String require;

    public Htaccess() {
        this.load();
    }

    public void load() {
        try {
            String fileContents = new String(Files.readAllBytes(Paths.get("public_html/.htaccess")));
            parse(fileContents);
        } catch (IOException e) {
            System.out.println("failed to read file");
            e.printStackTrace();
        }
    }
    //TODO:
    //implement AuthUserFile
    public void parse(String fileContents) {
        String[] tempLineSplit = fileContents.split("\n");
        for (int index = 0; index < tempLineSplit.length; index++) {
            String[] temp = tempLineSplit[index].split(" ", 2);
            //System.out.println(temp[0]);
            switch (temp[0]) {
                case "AuthUserFile":
                    //userFile = temp[1];
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
                    //TODO: find default case?
                    System.out.println("??");
                    break;
            }
        }
    }
    public boolean isAuthorized(String username, String password) {
        return false;
    }
    //for Tests
    @Override
    public String toString() {
        return "Htaccess{" +
                "userFile=" + userFile +
                ", authType='" + authType + '\'' +
                ", authName='" + authName + '\'' +
                ", require='" + require + '\'' +
                '}';
    }

    public static void main (String[] args) {
        System.out.println("TESTSTST");
        Htaccess test = new Htaccess();
        System.out.println(test.toString());
    }
}
