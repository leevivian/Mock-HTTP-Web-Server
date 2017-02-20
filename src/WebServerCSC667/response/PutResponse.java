package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class PutResponse extends Response {
    public PutResponse(Resource resource){
        super(resource, 201);
        reasonPhrase = "OK";
        setSendBody(true);
    }
}
