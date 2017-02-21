package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class NotModifiedResponse extends Response{
    public NotModifiedResponse(Resource resource){
        super(resource, 304);
        reasonPhrase = "Not Modified";
        setSendBody(false);
    }
}
