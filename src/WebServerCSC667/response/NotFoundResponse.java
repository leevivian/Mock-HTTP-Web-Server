package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class NotFoundResponse extends Response{
    public NotFoundResponse(Resource resource){
        super(resource, 404);
        reasonPhrase = "Not Found";
        setSendBody(false);
    }
}
