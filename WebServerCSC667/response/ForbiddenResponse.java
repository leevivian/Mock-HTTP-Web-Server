package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class ForbiddenResponse extends Response {
    public ForbiddenResponse(Resource resource){
        super(resource, 403);
        reasonPhrase = "Forbidden";
        setSendBody(false);
    }
}
