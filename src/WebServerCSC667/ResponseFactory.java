package WebServerCSC667;

import java.io.File;

/**
 * Created by rain2 on 2/3/2017.
 */
public class ResponseFactory {


    Response getResponse(Request request, Resource resource) {
        //TODO: Access checks

        if (request.getVerb() != "PUT") {
            //if file doesn't exist
            if (new File(resource.getAbsolutePath()).isFile() == true && (resource.isScript() == true)) {
                //check if script alias
                //TODO: add script execusion
                return new Response(200);
            }
            else if (resource.isScript() == false) {
                switch(request.getVerb()) {
                    //TODO code for each switch case
                    case "PUT":
                        return new Response(201);
                    case "DELETE":
                        return new Response(204);
                    case "GET":
                        //TODO: returns for POST & HEAD in get
                        return new Response(200);
                    default:
                }
            }
            else {
                return new Response(404);
            }
        }
        //TODO is it 400?
        return new Response(400);
    }

}



