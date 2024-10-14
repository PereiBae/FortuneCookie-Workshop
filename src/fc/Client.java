package fc;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java -cp fortunecookie.jar fc.Client <host>:<port>");
            System.exit(1);
        }

        String[] hostport = args[0].split(":");
        if (hostport.length != 2) {
            System.err.println("Invalid format. Please provide <host>:<port>.");
            System.exit(1);
        }

        System.out.println("Connecting to the server");
        Socket sock = new Socket(hostport[0], Integer.parseInt(hostport[1]));

        System.out.println("Connected");

        Console cons = System.console();

        OutputStream os = sock.getOutputStream();
        PrintWriter printWriter = new PrintWriter(os, true);
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        while (true) {

            System.out.println("Type 'get-cookie' to receive a cookie or 'close' to disconnect.");

            String theMessage = cons.readLine(">>> Input: ");

            if (theMessage == null || theMessage.trim().isEmpty()) {
                System.out.println("Invalid input. Please try again. \n");
                continue; // Skip empty or invalid input
            }
            
            if (theMessage.equalsIgnoreCase("close")) {
                System.out.println("Closing connection...");
                break;
            }

            if (!theMessage.equalsIgnoreCase("get-cookie")) {
                System.out.println("Invalid command. Please enter 'get-cookie' or 'close'. \n");
                continue;  // Re-prompt for valid input
            }

            bw.write(theMessage + "\n");

            bw.flush();
            os.flush();

            String fromServer = br.readLine();
            if (fromServer != null && fromServer.startsWith("cookie-text")){
                System.out.println(">>> Server: " + fromServer.substring("cookie-text".length()) + "\n");
            } else {
                System.out.println(">>> Server: Invalid response received.\n");
            }

        }
    }

}