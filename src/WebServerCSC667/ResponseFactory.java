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
        //TODO: implment error 500 - this should be a wrapper around Worker
        //TODO: Access checks

        if (resource.isProtected() == true){
            //401 and 403 erros here
            //TODO: loop file path for all subdirectorys for .htaaccess file
            String[] parse = resource.getAbsolutePath().split("/");
            for (int index = 0; index < parse.length; index++){
                if (resource.isProtected()){
                  Htaccess hta = new Htaccess();

                  try {
                      Htpassword htp = new Htpassword("public_html/example.htpasswd");
                      //TODO: need request auth headers
                      if(request.getAuthHeader() == null){
                          return new UnauthorizedResponse(resource);
                      } else {
                          if (htp.isAuthorized(request.getAuthHeader()) == false) {
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
                }
            }
            //if auth header -> auth
        }

        //start response
        //else if (response.isProtected() == false || (response.isProtected() && //is VALID PW))

        if (!request.getVerb().equals("PUT")) {
            //if file doesn't exist
            if (new File(resource.getAbsolutePath()).isFile() == true && (resource.isScript() == true)) {

                if (resource.isModifiedScriptAliasURI()) {
                    try {
                        resource.setHeaders(request.getHeaders());
                        return new ScriptResponse(resource, 200);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } else if (resource.isScript() == false) {
                File file = new File(resource.getAbsolutePath());

                switch (request.getVerb()) {
                    //TODO: make dir if content type = null
                    case "PUT":
                        if (resource.getContentType() == null){
                            new File(resource.getAbsolutePath()).mkdirs();
                            System.out.println("MAKE NEW DIR");
                            return new PutResponse(resource);

                        }
                        //if file already exists
                        if (file.isFile() == true) {
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
                        if (file.isFile() == true) {
                            file.delete();
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
                            resource.setLastModified(new Date (file.lastModified()));
                            return new HeadResponse(resource);
                        //} else return new NotModifiedResponse(resource);
                    default:
                        return new BadRequestResponse(resource);
                }
            } else {
                return new NotFoundResponse(resource);
            }
        }
        //TODO is it 400?
        return new BadRequestResponse(resource);
    }

}



