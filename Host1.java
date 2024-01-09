import java.net.*;

public class Host1 {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();//from ds
            DatagramSocket socket1 = new DatagramSocket(5555);//sent to h2//

            // InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            InetAddress serverAddress = InetAddress.getByName("10.190.33.184");
            int serverPort = 9876;

            // Send a request to DS
            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            // Receive the response from DS
            byte[] receiveData = new byte[1024];
            String dateTime;
            int i = 1;

            byte[] receiveData1 = new byte[1024];
            DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
            socket1.receive(receivePacket1);
            String cd;
            do {
                InetAddress clientAddress = receivePacket1.getAddress();
                int clientPort = receivePacket1.getPort();
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                System.out.println("recieved from server");
                dateTime = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("line " + i++ + " " + dateTime);
                
                System.err.println(clientAddress + ":" + clientPort);
                byte[] datetime = dateTime.getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(datetime, datetime.length, clientAddress, clientPort);
                socket1.send(sendPacket1);
                System.out.println("sent to client");
                receiveData1 = new byte[1024];
                receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
                socket1.receive(receivePacket1);
                System.out.println("recieved from client");
                if(!dateTime.substring(0,3).equals("end")){
                byte[] valid = new byte[1024];
                sendPacket = new DatagramPacket(valid, valid.length, serverAddress, serverPort);
                socket.send(sendPacket);
                System.out.println("sent to server");}
                cd = "end";
            } while (!dateTime.substring(0,3).equals("end"));

            socket.close();
            socket1.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
