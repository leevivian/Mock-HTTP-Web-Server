package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class PutResponse extends Response {
    public PutResponse(Resource resource){
        super(resource, 201);
        reasonPhrase = "Created";
        setSendBody(true);
    }
}
