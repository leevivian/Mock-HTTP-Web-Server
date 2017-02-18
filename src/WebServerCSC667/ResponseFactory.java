package WebServerCSC667;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by rain2 on 2/3/2017.
 */
public class ResponseFactory {


    public static Response getResponse(Request request, Resource resource) {
        //TODO: implment error 500
        //TODO: Access checks
        if (resource.isProtected() == true){
            //401 and 403 erros here
        }
        //start response
        //else if (resource.isProtected() == false || (resource.isProtected() && //is VALID PW))
        if (request.getVerb() != "PUT") {
            //if file doesn't exist
            if (new File(resource.getAbsolutePath()).isFile() == true && (resource.isScript() == true)) {
                //check if script alias
                //TODO: add script execusion
                return new Response(resource, 200);
            } else if (resource.isScript() == false) {
                File file = new File(resource.getAbsolutePath());
                switch (request.getVerb()) {
                    case "PUT":
                        //if file already exists
                        if (file.isFile() == true) {
                            return new Response(resource, 400);
                        }
                        //do put
                        else {
                            byte data[] = request.getBody().getBytes();
                            Path filePUT = Paths.get(resource.getAbsolutePath());
                            try {
                                Files.write(filePUT, data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Files.write(file, data, StandardOpenOption.APPEND);
                            return new Response(resource, 201);
                        }

                    case "DELETE":
                        if (file.isFile() == true) {
                            file.delete();
                            return new Response(resource, 204);
                        } else {
                            System.out.println("File Doesn't exist");
                            return new Response(resource, 400);
                        }

                        //TODO:
                    case "GET":
                        if (resource.isModifiedURI() == true) {
                            try {
                                resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                                //System.out.println(resource.getBody().)
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return new Response(resource, 200);
                        } else return new Response(resource, 304);

                    case "POST":
                        try {
                            resource.setBody(Files.readAllBytes(Paths.get(resource.getAbsolutePath())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new Response(resource, 200);

                    case "HEAD":
                        if (resource.isModifiedURI() == true) {
                            return new Response(resource, 200);
                        } else return new Response(resource, 304);
                    default:
                        return new Response(resource, 400);
                }
            } else {
                return new Response(resource, 404);
            }
        }
        //TODO is it 400?
        return new Response(resource,400);
    }

}



