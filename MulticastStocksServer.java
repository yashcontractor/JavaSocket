import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;  
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Random;

public class MulticastStocksServer {

	public String[] companies = {"Applet", "Googlet", "Teslat", "Metat"};
	public double[] companiesStockAverage = {160.00, 105.00, 190.00, 206.00};
	public String multicastChannel = "228.1.1.1";
	
    public MulticastStocksServer() {
        System.out.println("\n Stock Values Multicast Server Starting on multicast-channel-IP: "+multicastChannel+"\n");	
        try {
		    InetAddress mcastaddr = InetAddress.getByName(multicastChannel);	
			InetSocketAddress group = new InetSocketAddress(mcastaddr, 5555);			
			//below line identifies the server's physical interface through which multicast datagram-packets will be actually transmitted
			NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            MulticastSocket multicastSocket = new MulticastSocket();
            multicastSocket.joinGroup(group,nif);
            
            byte[] data;
            DatagramPacket packet;        
            while (true) {
                Thread.sleep(1000);
                String time = (new Date()).toString();
				String message = " "+time+"\n"+GetStockValues();
				data = message.getBytes();
                // System.out.println(" Sending stock values: \n" + message);
                packet = new DatagramPacket(data, message.length(), mcastaddr, 5555);
                
                multicastSocket.send(packet);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("UDP Multicast Time Server Terminated");
    }
	
	public String GetStockValues() {
		String stockReport = " ";
		Random random = new Random();
		double newStockValue;
		for (int i=0; i<companies.length; i++) {
			newStockValue = companiesStockAverage[i]+ (-5 + 10*random.nextDouble());
			stockReport = stockReport + companies[i] + ":  " + newStockValue + "\n "; };
		return stockReport;		
	}	
    
    public static void main(String args[]) {
        new MulticastStocksServer();
    }
}