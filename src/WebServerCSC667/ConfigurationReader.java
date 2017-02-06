package WebServerCSC667;

import java.io.*;

/**
 * Created by Vivian on 2/3/17.
 */

public class ConfigurationReader {

    private File file;
    private BufferedReader conf;
    private InputStream inputStream;
    private String line;

    public ConfigurationReader(String filename) {
        //Creates a new File instance by converting the given pathname string into an abstract pathname.
        file = new File(filename);
        load();
    }

    public boolean hasMoreLines() {

        return false;
    }

    public String nextLine() {
        try {
            line = conf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    public void load() {
        try {
            conf = new BufferedReader(new FileReader("conf/" + file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ConfigurationReader test = new ConfigurationReader("httpd.conf");
        System.out.println(test);
        System.out.println(test.nextLine());
        System.out.println(test.nextLine());
        System.out.println(test.nextLine());
        System.out.println(test.nextLine());
    }

}
