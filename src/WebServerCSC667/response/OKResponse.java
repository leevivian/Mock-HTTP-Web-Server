package WebServerCSC667.response;

import WebServerCSC667.Resource;

/**
 * Created by rain2 on 2/19/2017.
 */
public class OKResponse extends Response {

    public OKResponse(Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(true);
    }

}
