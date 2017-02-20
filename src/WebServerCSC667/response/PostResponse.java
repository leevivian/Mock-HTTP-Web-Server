package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class PostResponse extends Response {

    public PostResponse(Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(false);
    }
}
