import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    private DatagramSocket mini;

    private DatagramPacket UDPIn;

    private DatagramPacket UDPOut;

    private String sentence;

    private byte[] buf;


    public EchoServer(){
        try{
            buf = new byte[5000];
            mini = new DatagramSocket(1001);
            sentence = "";

        } catch(IOException e){
            System.err.println("could not open socket or recieve information");
        }

    }

    public void transform(){
        try {
            UDPIn = new DatagramPacket(buf, buf.length);
            mini.receive(UDPIn);
            sentence = new String(UDPIn.getData(), StandardCharsets.UTF_8);
            buf = sentence.getBytes();
            UDPOut = new DatagramPacket(buf, buf.length, UDPIn.getAddress(), UDPIn.getPort());
            mini.send(UDPOut);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        EchoServer theServer = new EchoServer();
        while(true){
            theServer.transform();
        }

    }
}
