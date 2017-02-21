package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class UnauthorizedResponse extends Response {
    public UnauthorizedResponse(Resource resource){
        super(resource, 401);
        reasonPhrase = "Unauthorized";
        setSendBody(true);
    }

}
