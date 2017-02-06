package WebServerCSC667;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Stream;

public class Request {
    private String uri;
    //private ? body;
    private String verb;
    private String httpVersion;
    private Dictionary<String, String> headers;

    private String myStr;

    public Request (String test){
        myStr = test;
        parse();
    }
    public Request (Stream client){

    }
    public void parse (){
        String[] arr = myStr.split("\n");

        // HTTP_METHOD IDENTIFIER HTTP_VERSION
        String[] firstLine = arr[0].split(" ");
        verb = firstLine[0];
        uri = firstLine[1];
        httpVersion = firstLine[2];

        headers = new Hashtable();
        int index = 1;
        while (arr[index].contains(": ")) { // or while != " " ?
            String[] headersArr = arr[index].split(": ");
            headers.put(headersArr[0], headersArr[1]);
            index++;
        }
    }
    //accessors
    // get something from ConfigurationReader?

    public static void main (String args[]) {
        Request myReq = new Request("GET /docs/index.html HTTP/1.1\n" +
                                         "Host: www.nowhere123.com\n" +
                                         "Accept: image/gif, image/jpeg, */*\n" +
                                         "Accept-Language: en-us\n" +
                                         "Accept-Encoding: gzip, deflate\n" +
                                         "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\n" +
                                         "    ");
    }
}
