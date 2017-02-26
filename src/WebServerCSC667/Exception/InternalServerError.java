package WebServerCSC667;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class InternalServerError extends RuntimeException {

    InetAddress inetAddress;

    public InternalServerError(Socket socket){

        super();
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());

        ps.println("HTTP/1.1 500 Internal Server Error");
        ps.println("Date: " + new Date());
        ps.println("Server: " + getServerName());

        ps.flush();
        ps.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerName() {
        try {
            return inetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Apache";
    }
}
