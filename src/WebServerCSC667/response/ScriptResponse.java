package WebServerCSC667.response;

import WebServerCSC667.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Date;

public class ScriptResponse extends Response {

    private String scriptResponseBody = "";
    private String readinScriptOutputCurrentLine;
    private String contentType = null;

    public ScriptResponse (Resource resource) throws IOException{
        super(resource, 200);

        ProcessBuilder processBuilder = new ProcessBuilder(resource.getAbsolutePath());
        sendEnvironmentVariables(processBuilder.environment());
        Process executeScript = processBuilder.start();

        BufferedReader readInScriptOutput = new BufferedReader(new InputStreamReader(executeScript.getInputStream()));
        contentType = readInScriptOutput.readLine();

        while((readinScriptOutputCurrentLine = readInScriptOutput.readLine()) != null){
            scriptResponseBody += readinScriptOutputCurrentLine + "\n";
        }

        resource.setBody(scriptResponseBody.getBytes());
    }

    public void sendEnvironmentVariables(Map<String, String> environment) {

        Iterator iterateRequestHeaders = resource.getHeaders().entrySet().iterator();
        while (iterateRequestHeaders.hasNext()) {
            Map.Entry headerLine = (Map.Entry)iterateRequestHeaders.next();
            environment.put("HTTP_" + headerLine.getKey().toString().toUpperCase(), headerLine.getValue().toString().toUpperCase());
            iterateRequestHeaders.remove();
        }
        if (resource.getQueryString() != null) {
            environment.put("QUERY_STRING", resource.getQueryString());
        }

        environment.put("SERVER_PROTOCOL", "HTTP/" + httpVersion);
    }



    @Override
    public void send(OutputStream out){
        PrintStream ps = new PrintStream(out);

        ps.println("HTTP/" + httpVersion + " " + code + " " + reasonPhrase);
        ps.println("Date: " + new Date());
        ps.println("Server: " + getServerName());
        ps.println("Content-Type: " + contentType);
        ps.println("Content-Length: " + scriptResponseBody.length());
        ps.println();
        ps.println(scriptResponseBody);

        ps.flush();
        ps.close();
    }
}
