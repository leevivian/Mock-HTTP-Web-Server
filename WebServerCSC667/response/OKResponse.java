package WebServerCSC667.response;

import WebServerCSC667.Resource;

public class OKResponse extends Response {

    public OKResponse(Resource resource){
        super(resource, 200);
        reasonPhrase = "OK";
        setSendBody(true);
    }

}
