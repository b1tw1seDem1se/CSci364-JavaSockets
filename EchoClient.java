//package hw1

//please don't use wildcards :)
import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
        //socket isn't being closed
            System.out.println("Attempting to establish connection with " + hostName + " on port " + portNumber + "...");
            Socket clientSideSocket = new Socket(hostName, portNumber);
            PrintWriter clientSideMessageWriter = new PrintWriter(clientSideSocket.getOutputStream(), true);
            BufferedReader clientSideMessageReader = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
            BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
	    
	    //reading console input, will never receive null (don't use Ctrl + C to kill process (client only))
            
        receiveMessage(clientSideMessageReader);

            /* boolean invalidInput = true;
            while (invalidInput) {
                try {
                    userInput = stdIn.readLine();
                    Integer.parseInt(userInput);
                    invalidInput = false;
                } catch (NumberFormatException e) {
                    System.out.print("\nPlease input an integer value!\n" +
                                      ">>> ");
                    invalidInput = true;
                }
            } */
            
            

            //System.out.println(in.readLine());


            while ((userInput = systemReader.readLine()) != null) {
                clientSideMessageWriter.println(userInput);
                System.out.println("echo: " + clientSideMessageReader.readLine());












            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }

    public static void receiveMessage(BufferedReader clientSideMessageReader) throws IOException {
        while (clientSideMessageReader.ready()) {
            System.out.print("\n" + clientSideMessageReader.readLine());
        }
    }
}
