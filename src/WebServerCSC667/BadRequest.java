package WebServerCSC667;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

public class BadRequest extends RuntimeException{

    public BadRequest(Socket socket) {
        super();
        try {
            PrintStream ps = new PrintStream(socket.getOutputStream());

            ps.println("HTTP/1.1 400 Bad Request");
            ps.println("Date: " + new Date());
            ps.println("Server: ");
            ps.println("Content-Type: \n");

            ps.flush();
            ps.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
