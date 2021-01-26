import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MasterServer {
    private static String[] options = { "echo", "reverse","upper", "lower", "caesar", "???"};

    private ServerSocket server;

    private Socket serverSocket;

    private BufferedReader socketIn;

    private PrintWriter socketOut;

    private String sentence;

    private DatagramSocket UDPSocket;

    private DatagramPacket UDPMessage;

    private DatagramPacket UDPResponse;

    private InetAddress ia;


    public MasterServer(int port){
        try {
            server = new ServerSocket(port);
            System.out.println("Awaiting connection to the client");
            acceptClient();
            System.out.println("Connection accepted starting communication");
            socketIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            socketOut = new PrintWriter(serverSocket.getOutputStream(), true);
            UDPSocket = new DatagramSocket();
            ia = InetAddress.getLocalHost();
            sentence = null;
        }catch(UnknownHostException e){
            System.err.println("could not get the address of the host");
        }
        catch(IOException e){
            e.printStackTrace();
            System.err.println("Could not setup server, now exiting");
            System.exit(-1);
        }
    }

    public void readSentenceFromClient() {
        try{
            sentence = socketIn.readLine();
            System.out.println("sentence is: "+sentence);
        }catch(IOException e){
            System.err.println("Error reading input from client");
        }
    }

    public void communicate(){
        String clientRequest;
        String serverResponse;
        int choice = 0;
        while(true){
            try{
                clientRequest = socketIn.readLine();
                clientRequest = clientRequest.toLowerCase();
                for(int i = 0; i< options.length; i++){
                    if(options[i].equals(clientRequest)){
                        choice = i+1;
                        break;
                    }
                    else {
                        choice = 0;
                    }
                }
                if(choice == 0) {
                    socketOut.println("quit");
                    return;
                }

                performFunction(choice);
            }catch(NumberFormatException e){
                System.err.println("Error changing given string to number");
            }
            catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    private void performFunction(int choice){
        int port = choice+2000;
        byte [] message = sentence.getBytes();
        try {

            UDPMessage = new DatagramPacket(message, message.length, ia, port);
            UDPSocket.send(UDPMessage);
            UDPResponse = new DatagramPacket(message, message.length);
            UDPSocket.receive(UDPResponse);
            String response = new String(UDPResponse.getData(), StandardCharsets.UTF_8);
            socketOut.println(response);

        }catch(IOException e){
            System.err.println("error reading or writing to mini servers");
        }
    }

    public void acceptClient(){
        try {
            serverSocket = server.accept();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean stillOpen(){
        return serverSocket.isClosed();
    }

    public static void main(String [] args){
        MasterServer theServer = new MasterServer(8000);
        while(true) {
            if(theServer.stillOpen())
                theServer.acceptClient();
            theServer.readSentenceFromClient();
            theServer.communicate();
        }
    }




}
