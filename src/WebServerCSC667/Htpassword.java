package WebServerCSC667;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Base64;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import java.io.IOException;
import java.util.Objects;

public class Htpassword extends ConfigurationReader {
    private HashMap<String, String> passwords;

    public Htpassword( String filename ) throws IOException {
        super( filename );
        System.out.println( "Password file: " + filename );

        this.passwords = new HashMap<String, String>();
        this.load();
        while(hasMoreLines() == true){
            this.parseLine(nextLine());
        }
    }

    protected void parseLine( String line ) {
        String[] tokens = line.split( ":" );

        if( tokens.length == 2 ) {
            passwords.put( tokens[ 0 ], tokens[ 1 ].replace( "{SHA}", "" ).trim() );
        }
    }

    public boolean isAuthorized( String authInfo ) {
        // authInfo is provided in the header received from the client
        // as a Base64 encoded string.
        String credentials = new String(
                Base64.getDecoder().decode( authInfo ),
                Charset.forName( "UTF-8" )
        );

        // The string is the key:value pair username:password
        String[] tokens = credentials.split( ":" );

        // TODO: implement this
        if(verifyPassword(tokens[0],tokens[1]) == true){
            System.out.println("isAuth verify TURE");
            return true;

        }
        System.out.println("isAuth verify FALSE");

        return false;
    }

    private boolean verifyPassword( String username, String password ) {
        // encrypt the password, and compare it to the password stored
        // in the password file (keyed by username)
        // TODO: implement this
        System.out.println("Username: " + username);
        System.out.println("password: " + password);
        System.out.println("encryptClearPassword(password)" + encryptClearPassword(password));
        System.out.println("usernamepw: "+ passwords.get(username));
        if (Objects.equals(encryptClearPassword(password), passwords.get(username))){
            System.out.println("verify = true");
            return true;
        }
        System.out.println("verify = false");
        return false;
    }

    private String encryptClearPassword( String password ) {
        // Encrypt the cleartext password (that was decoded from the Base64 String
        // provided by the client) using the SHA-1 encryption algorithm
        try {
            MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
            byte[] result = mDigest.digest( password.getBytes() );

            return Base64.getEncoder().encodeToString( result );
        } catch( NoSuchAlgorithmException e ) {
            return "";
        }
    }

    public void load(){
            try {
                setFileContents(new String(Files.readAllBytes(Paths.get(String.valueOf(super.getFile())))));
                setParsedFile(getFileContents().split("\n"));
            } catch (IOException e) {
                System.out.println("failed to read file");
                e.printStackTrace();
            }
        }

    public static void main (String[] args){
        try {
            Htpassword test = new Htpassword("./public_html/example.htpasswd");
            test.verifyPassword("jrob","password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}