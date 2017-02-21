package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class DeleteResponse extends Response {
    public DeleteResponse(Resource resource){
        super(resource, 204);
        reasonPhrase = "No Content";
        setSendBody(false);
    }
}
