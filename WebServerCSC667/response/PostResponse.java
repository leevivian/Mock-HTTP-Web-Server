package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class PostResponse extends Response {

    public PostResponse(Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(false);
    }
}
