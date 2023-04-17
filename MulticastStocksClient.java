/*
 * EECS 3431: Socket Programming Assignment
 * Yash Contractor
 * April 17, 2023
 * Student Number: 216290751
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Scanner;
import java.util.Arrays;

public class MulticastStocksClient {
    public String[] companies = {"Applet", "Googlet", "Teslat", "Metat"};
    public double[] companiesStockAverage = {160.00, 105.00, 190.00, 206.00};
    public String multicastChannel = "228.1.1.1";
    public byte[] buffer = new byte[1024];
    private int option;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which stock-index you want to monitor?");
        System.out.println("Applet only - type '1'");
        System.out.println("Googlet only - type '2'");
        System.out.println("Teslat only - type '3'");
        System.out.println("Metat only - type '4'");
        System.out.println("All stocks - type '5'");

        int option = scanner.nextInt();
        if (option >= 1 && option <= 5) {
            System.out.println("You have chosen option: " + option);
            new MulticastStocksClient(option);
        } 
        else {
            System.out.println("Invalid input. Please type a number from 1 to 5.");
        }
    }

    public MulticastStocksClient(int option) {
        this.option = option;
        System.out.println("\n Stock Values Multicast Client Starting on multicast-channel-IP: " + multicastChannel + "\n");
        try {
            InetAddress multicastAddress = InetAddress.getByName(multicastChannel);
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            MulticastSocket multicastSocket = new MulticastSocket(5555);
            multicastSocket.joinGroup(new InetSocketAddress(multicastAddress, 5555), networkInterface);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("\ndata received from multicast-channel-IP: " + multicastChannel + ", server interface " + packet.getAddress() + "\n" + message);

                // parse message to extract stock values
                String[] stockValues = message.split("\n");
                for (String stockValue : stockValues) {
                    String[] values = stockValue.split(":");
                    if ((option == 5 || Arrays.asList(companies).contains(values[0])) && (option == 5 || Integer.parseInt(values[0].substring(values[0].length() - 1)) == option)) {
                        double stockValueDouble = Double.parseDouble(values[1].trim());
                        System.out.println(values[0] + " stock value: " + stockValueDouble);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("UDP Multicast Time Server Terminated");
    }
}

