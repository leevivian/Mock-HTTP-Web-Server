package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class BadRequestResponse extends Response{
    public BadRequestResponse(Resource resource){
        super(resource, 400);
        reasonPhrase = "Bad Request";
        setSendBody(false);
    }
}
