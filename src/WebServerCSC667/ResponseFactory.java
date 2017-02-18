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


    public Response getResponse(Request request, Resource resource) {
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
                return new Response(resource,200);
            }
            else if (resource.isScript() == false) {

                switch(request.getVerb()) {
                    //TODO code for each switch case
                    case "PUT":
                        //if file already exists
                        if (new File(resource.getAbsolutePath()).isFile() == false){
                            return new Response(resource, 404);
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
                        File file = new File(resource.getAbsolutePath());
                        if (file.isFile() == true){
                        file.delete();
                        return new Response(resource,204);
                        }
                        else {
                            System.out.println("File Doesn't exist");
                            return  new Response(resource, 400);
                        }
                    case "GET":
                        //TODO: returns for POST & HEAD in get
                        return new Response(resource, 200);
                    default:
                        return new Response(resource, 200);
                }
            }
            else {
                return new Response(resource, 404);
            }
        }
        //TODO is it 400?
        return new Response(resource,400);
    }

}



