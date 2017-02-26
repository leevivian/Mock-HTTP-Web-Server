package WebServerCSC667;

import WebServerCSC667.configuration.Htaccess;
import WebServerCSC667.configuration.Htpassword;
import WebServerCSC667.response.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseFactory {

    public static Response getResponse(Request request, Resource resource) {

        File resourceFile = new File(resource.getAbsolutePath());

        if (resource.isProtected() == true) {
            Htaccess hta = new Htaccess(resource.getHtaccessPath());
            Htpassword htp = hta.getHtpassword();
            if (request.getAuthHeader() == null) {
                return new UnauthorizedResponse(resource, hta);
            } else if (request.getAuthHeader() != null) {
                String parseAuthorizationHeader[] = request.getAuthHeader().split("\\s+", 2);
                String encodedCredentials = parseAuthorizationHeader[1];
                if (htp.isAuthorized(encodedCredentials) == false) {
                    return new ForbiddenResponse(resource);
                }
            }

        }

        if (!request.getVerb().equals("PUT")) {
            if (resourceFile.isFile() && (resource.isScript())) {

                if (resource.isModifiedScriptAliasURI()) {
                    try {
                        resource.setHeaders(request.getHeaders());
                        return new ScriptResponse(resource);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (!resourceFile.isFile() && !resource.isScript()) {
                return new NotFoundResponse(resource);
            }
        } else if (request.getVerb().equals("PUT")) {
            if (resource.isModifiedScriptAliasURI()) {
                try {
                    resource.setHeaders(request.getHeaders());
                    return new ScriptResponse(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        switch (request.getVerb()) {
            case "PUT":
                if (resource.getContentType() == null) {
                    new File(resource.getAbsolutePath()).mkdirs();
                    return new PutResponse(resource);
                }

                if (resourceFile.isFile()) {
                    return new BadRequestResponse(resource);
                } else {
                    resource.setBody(request.getBody().getBytes());
                    Path filePUT = Paths.get(resource.getAbsolutePath());
                    try {
                        Files.write(filePUT, resource.getBody());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new PutResponse(resource);
                }

            case "DELETE":
                if (resourceFile.isFile()) {
                    resourceFile.delete();
                    return new DeleteResponse(resource);
                } else {
                    System.out.println("File Doesn't exist");
                    return new BadRequestResponse(resource);
                }

            case "GET":

                try {
                    resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                    resource.setLastModified(new Date(resourceFile.lastModified()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                resource.setModifiedResource(true);
                return new OKResponse(resource);

            case "POST":
                try {
                    resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BadRequestResponse(resource);
                }
                return new PostResponse(resource);

            case "HEAD":
                //if (resource.isModifiedURI() == true) {
                //TODO: GRADING CHECKLIST - Simple caching (HEAD results in 200 with Last-Modified header)
                resource.setLastModified(new Date(resourceFile.lastModified()));
                return new HeadResponse(resource);
            //} else return new NotModifiedResponse(resource);
            default:
                return new BadRequestResponse(resource);
        }
    }
}



