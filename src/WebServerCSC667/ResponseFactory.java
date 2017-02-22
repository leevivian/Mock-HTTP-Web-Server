package WebServerCSC667;

import WebServerCSC667.response.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ResponseFactory {

    public static Response getResponse(Request request, Resource resource) {

        File resourceFile = new File(resource.getAbsolutePath());

        //TODO: implment error 500 - this should be a wrapper around Worker
        //TODO: Access checks

        if (resource.isProtected() == true){
            //401 and 403 erros here
            Htaccess hta = new Htaccess();
            try {

                // TODO: How to get the location of htpassword
                Htpassword htp = new Htpassword("public_html/example.htpasswd");
                if(request.getAuthHeader() == null){
                    return new UnauthorizedResponse(resource);
                } else {
                    // Example: Authorization: Basic QWxhZGRpbjpPcGVuU2VzYW1l
                    String parseAuthorizationHeader[] = request.getAuthHeader().split("\\s+");
                    String encodedCredentials = parseAuthorizationHeader[1];
                    if (htp.isAuthorized(encodedCredentials) == false) {
                        return new ForbiddenResponse(resource);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

                  /*
                    //form username, password
                  if(hta.isAuthorized(username, password) == false){
                      return new ForbiddenResponse(resource);
                  }

                  */

            //if auth header -> auth
        }

        //start response
        //else if (response.isProtected() == false || (response.isProtected() && //is VALID PW))

        if (!request.getVerb().equals("PUT")) {
            //if file doesn't exist
            if (resourceFile.isFile() == true && (resource.isScript() == true)) {

                if (resource.isModifiedScriptAliasURI()) {
                    try {
                        resource.setHeaders(request.getHeaders());
                        return new ScriptResponse(resource, 200);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!resourceFile.isFile() && !resource.isScript()) {
                return new NotFoundResponse(resource);
            }
        }

        switch (request.getVerb()) {
            //TODO: make dir if content type = null
            case "PUT":
                if (resource.getContentType() == null){
                    new File(resource.getAbsolutePath()).mkdirs();
                    System.out.println("MAKE NEW DIR");
                    return new PutResponse(resource);

                }
                //if file already exists
                if (resourceFile.isFile() == true) {
                    return new NotFoundResponse(resource);

                }
                //do put
                else {
                    resource.setBody(request.getBody().getBytes());
                    Path filePUT = Paths.get(resource.getAbsolutePath());
                    try {
                        Files.write(filePUT, resource.getBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Files.write(file, data, StandardOpenOption.APPEND);
                    return new PutResponse(resource);
                }

            case "DELETE":
                if (resourceFile.isFile() == true) {
                    resourceFile.delete();
                    return new DeleteResponse(resource);
                } else {
                    System.out.println("File Doesn't exist");
                    return new BadRequestResponse(resource);
                }

                //TODO:
            case "GET":
                // if (response.isModifiedURI() == true) {
                try {
                    resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                    //System.out.println(response.getBody().)
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return new OKResponse(resource);

            //} else rreturn new NotModifiedResponse(resource);

            case "POST":
                try {
                    resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                } catch (IOException e) {
                    e.printStackTrace();
                    // TODO: Return bad response for failure to find file or smth
                }
                return new PostResponse(resource);

            case "HEAD":
                //if (resource.isModifiedURI() == true) {
                //TODO: GRADING CHECKLIST - Simple caching (HEAD results in 200 with Last-Modified header)
                resource.setLastModified(new Date (resourceFile.lastModified()));
                return new HeadResponse(resource);
            //} else return new NotModifiedResponse(resource);
            default:
                return new BadRequestResponse(resource);
        }
    } // end getResponse

}



