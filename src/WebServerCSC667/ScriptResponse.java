package WebServerCSC667;

import java.io.*;
import java.util.Map;

public class ScriptResponse extends Response {

    public ScriptResponse (Resource resource, int code) throws IOException{
        super(resource, code);

        ProcessBuilder processBuilder = new ProcessBuilder("public_html/cgi-bin/perl_env");
        //processBuilder.directory(new File("/public_html/cgi-bin/perl_env"));
        setEnvironment(processBuilder.environment());
        Process process = processBuilder.start();

        BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        while(inStreamReader.readLine() != null){
            System.out.println("hi " + inStreamReader.readLine());
        }
    }

    public void setEnvironment(Map<String, String> environment) {
        resource.getHeaders().forEach((key, value) -> {
            environment.put("HTTP_" + key.toString().toUpperCase(), value.toString().toUpperCase());
        });
    }

    @Override
    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);
        ps.println(responseString);
        /*
        ps.println("HTTP/" + httpVersion + " " +code + " " + reasonPhrase);
        ps.println("Date: " + new Date() + "");
        ps.println("Server: " + "Hey there I'm the Server" + "");
        ps.println("Content-Type: " + "Hey there I'm the content type" + "\n");
*/

        ps.flush();
        ps.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
