//package hw1

import java.net.*;
import java.io.*;
import java.util.Random;

public class EchoServer {

    private static ServerSocket serverSideSocket = null;
    private static Socket clientSideSocket = null;
    private static PrintWriter serverSideMessageWriter = null;
    private static BufferedReader in = null;

    private static int guessingRangeUpperBound = 10;
    private static int portNumber = -1;

    public static void main(String[] args) throws IOException {

       parseArguments(args);
       establishConnectionToClient();

        try {
	    //not closing server socket or client socket
            
            String inputLine;

            sendMessageToClient("Welcome to the high-low guessing game!");
            generateRandomNumber(guessingRangeUpperBound);
            sendMessageToClient("Please guess a number in the following range: [0, " + guessingRangeUpperBound + ")\n>>> ");
	    //will never receive null
            while ((inputLine = in.readLine()) != null) {
                serverSideMessageWriter.println(inputLine);



            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        
    }

    public static void generateRandomNumber(int guessingRangeUpperBound) {
        System.out.println("Generating random number in the following range: [0, " + guessingRangeUpperBound + ")...");
        Random randomNumberGenerator = new Random();
        int randomNumber = randomNumberGenerator.nextInt(guessingRangeUpperBound);
        System.out.println("Random number (" + randomNumber + ") generated!");
    }

    public static void sendMessageToClient(String message) {
        System.out.println("Sending '" + message + "'...");
        serverSideMessageWriter.println(message);
        System.out.println("Message received!");
    }

    public static void parseArguments(String[] args) {
        try {
            if (args.length == 1) {
                portNumber = Integer.parseInt(args[0]);
            }
            else if (args.length == 2) {
                portNumber = Integer.parseInt(args[0]);
                guessingRangeUpperBound = Integer.parseInt(args[1]);
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Usage: java EchoServer <port number> (optional)<guessing range upper bound integer>");
        }

        if (args.length == 1) {
            portNumber = Integer.parseInt(args[0]);
        }
        else if (args.length == 2) {
                guessingRangeUpperBound = Integer.parseInt(args[1]);
        }
        else {
            System.err.println("Usage: java EchoServer <port number> (optional)<guessing range upper bound>");
            System.exit(1);
        }
    }

    public static void establishConnectionToClient() throws IOException {
        serverSideSocket = new ServerSocket(portNumber);
        System.out.println("Waiting for client...");
        clientSideSocket = serverSideSocket.accept();
        System.out.println("Client found! Establishing I/O streams...");
        serverSideMessageWriter = new PrintWriter(clientSideSocket.getOutputStream(), true); 
        in = new BufferedReader(new InputStreamReader(clientSideSocket.getInputStream()));
        System.out.println("I/O streams established!");
    }
}
