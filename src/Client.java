import java.io.*;
import java.net.Socket;

public class Client {
    private String hostName;

    private int portNumber;

    private Socket clientSocket;

    private BufferedReader socketIn;

    private PrintWriter socketOut;

    private BufferedReader stdIN;

    private String sentence;

    public Client(String host, int port){
        hostName = host;
        portNumber = port;
        try {
            clientSocket = new Socket(hostName, portNumber);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream() ,true);
            stdIN = new BufferedReader(new InputStreamReader(System.in));
            sentence = null;
        }catch(IOException e){
            System.err.println("Could not setup client socket with given host name and port number!");
            System.exit(-1);
        }
    }
    public void readSentence(){
        while(true){
            try{
                System.out.println("Please enter the sentence that you would like to perform transformations on");
                sentence = stdIN.readLine();
                socketOut.println(sentence);
            }catch(IOException e){
                System.err.println("error reading sentence please enter a new one try again");
            }
            if(sentence != null)
                break;
        }
    }
    public void communicate(){
       while (true){
           System.out.println(displayMenu());
           String clientRequest;
           String serverResponse;

           try{
               clientRequest = stdIN.readLine();
               socketOut.println(clientRequest);

               serverResponse = socketIn.readLine();
               if(serverResponse.toLowerCase().equals("quit"))
                   return;
               System.out.println(serverResponse);
           }catch(IOException e){
               e.printStackTrace();
           }

        }
    }
    private String displayMenu(){
        String s =
                "Your entered sentence is: \""+sentence+"\"\n"+
                        "Please choose a transformation you would like to be performed on the sentence" +
                        " by typing the name of the tranformation from the list.\n"+
                        "-----------------------------------------------------------------------------\n"+
                        "1. Echo\n"+
                        "2. Reverse\n"+
                        "3. Upper\n"+
                        "4. Lower\n"+
                        "5. Caesar\n"+
                        "6. ????\n"+
                        "7. type anything not on list to end connection\n";
        return s;
    }

    public static void main(String [] args){
        int port = 0;
        String name = "";
        try{
            name = args[0];
            port = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            e.printStackTrace();
            System.err.println("Please enter a number as your second argument for port number");
            System.exit(-1);
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            System.err.println("Too little arguments please enter both a hostname/ip and port number as second");
            System.exit(-1);
        }
        Client theClient = new Client(name, port);
        theClient.readSentence();
        theClient.communicate();
    }
}
