package fc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.*;

public class Server{

    public static void main (String[] args) throws IOException {

        int port = 3000;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        String file = args[1];
        ServerSocket server = new ServerSocket(port);

        Cookie cookieManager = new Cookie(file);

        while (true){
            System.out.println("Waiting for connection");
            Socket sock = server.accept();

            System.out.println("Got a new conection");

            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader serverBr = new BufferedReader(reader);

            OutputStream os = sock.getOutputStream();
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            String fromClient = serverBr.readLine();
            while (fromClient != null){

                System.out.println("Received command from client: " + fromClient);

                if(fromClient.equalsIgnoreCase("get-cookie")){
                    String randomCookie = cookieManager.getCookie();
                    String response = "cookie-text " + randomCookie;
                    bw.write(response + "\n");
                    bw.flush();
                    System.out.println("Sent cookie to client: " + randomCookie);
                } else if (fromClient.equalsIgnoreCase("close")){
                    System.out.println("Closing connection to client: " + sock.getRemoteSocketAddress());
                    break;
                } else {
                    System.out.println("Unknown command received: " + fromClient);
                }


                fromClient = serverBr.readLine();
            }
            
            System.out.println("Closing socket and streams for client: " + sock.getRemoteSocketAddress());
            bw.close();
            serverBr.close();
            sock.close();

        }

    }

}