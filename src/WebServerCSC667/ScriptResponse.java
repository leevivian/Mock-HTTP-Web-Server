package WebServerCSC667;

import java.io.*;
import java.util.Map;
import java.util.Date;

public class ScriptResponse extends Response {

    String currentLine;
    String responseBody = "";
    String contentType = null;

    public ScriptResponse (Resource resource, int code) throws IOException{
        super(resource, code);

        ProcessBuilder processBuilder = new ProcessBuilder(resource.getAbsolutePath());
        //processBuilder.directory(new File("/public_html/cgi-bin/perl_env"));
        setEnvironment(processBuilder.environment());
        Process process = processBuilder.start();

        BufferedReader inStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        contentType = inStreamReader.readLine();

        while((currentLine = inStreamReader.readLine()) != null){
            //System.out.println(inStreamReader.readLine());

            responseBody += currentLine + "\n";

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

        ps.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        ps.println("Date: " + new Date());
        ps.println("Server: CSC 667 Sailor Scouts");
        ps.println("Content-Length: " + responseBody.length());
        ps.println(contentType);
        ps.println();
        ps.println(responseBody);

        ps.flush();
        ps.close();
        try {
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
