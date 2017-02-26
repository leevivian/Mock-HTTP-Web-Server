package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class DeleteResponse extends Response {
    public DeleteResponse(Resource resource){
        super(resource, 204);
        reasonPhrase = "No Content";
        setSendBody(false);
    }
}
