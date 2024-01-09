import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DS {
    public static void main(String[] args) {
        try {
            while (true) {
                DatagramSocket socket = new DatagramSocket(9876); // Use a specific port, e.g., 9876
                System.out.println("Server running");
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                BufferedReader bf = new BufferedReader(new FileReader("data.txt"));
                String line;
                String message;
                InetAddress clientAddress=receivePacket.getAddress();
                int clientPort =  receivePacket.getPort();
                while ((line = bf.readLine()) != null) {
                    System.err.println(clientAddress + ":"     + clientPort);
                    String dateTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    message = dateTime + line;
                    System.out.println(message);
                    byte[] sendData = message.getBytes();
                    
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress,
                    clientPort);
                    socket.send(sendPacket);
                    receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(receivePacket);
                    clientAddress = receivePacket.getAddress();
                    clientPort = receivePacket.getPort();
                }
                message = "end";
                byte[] sendData = message.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                        clientAddress,
                        clientPort);
                socket.send(sendPacket);
                System.out.println("1 round");
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
