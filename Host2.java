import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Host2 {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            // InetAddress h1Address = InetAddress.getByName("127.0.0.1"); // Replace with the actual IP address of H1
            InetAddress h1Address = InetAddress.getByName("10.190.41.230"); // Replace with the actual IP address of H1
            int h1Port = 5555;
            int i = 1;
            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, h1Port);
            socket.send(sendPacket);
            // Date start = new SimpleDateFormat("HH:mm:ss").format(new Date());
            String data;
            do {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("line " + i++ + ": " + data);
                sendData = new byte[1024];
                sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, h1Port);
                socket.send(sendPacket)  ;
            } while (!data.substring(0,3).equals("end"));
            // Date end = new SimpleDateFormat("HH:mm:ss").format(new Date());
// sendData = new byte[1024];
            //  sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, h1Port);
            // socket.send(sendPacket);
            // String dateString = data.substring(0, 9);
            // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            // Date date = sdf.parse(dateString);
            // System.out.println(date);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
