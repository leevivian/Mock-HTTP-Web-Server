package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class BadRequestResponse extends Response{
    public BadRequestResponse(Resource resource){
        super(resource, 400);
        reasonPhrase = "Bad Request";
        setSendBody(false);
    }
}
