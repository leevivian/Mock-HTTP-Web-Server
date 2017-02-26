package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class NotFoundResponse extends Response{
    public NotFoundResponse(Resource resource){
        super(resource, 404);
        reasonPhrase = "Not Found";
        setSendBody(false);
    }
}
