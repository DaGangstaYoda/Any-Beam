package de.hfu.anybeam.networkCore.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import de.hfu.anybeam.networkCore.Client;
import de.hfu.anybeam.networkCore.DeviceType;

public class ClientTest {
	
	public static void main(String[] args) throws UnknownHostException {
		Client c1 = new Client(InetAddress.getLocalHost(), "Client 1", 1337, 
				"xx:xx:xx:xx:xx:xx:group", System.getProperty("os.name"), DeviceType.TYPE_LAPTOP);
		Client c2 = new Client(InetAddress.getLocalHost(), "Client 2", 1337, 
				"yy:yy:yy:yy:yy:yy:group", System.getProperty("os.name"), DeviceType.TYPE_DESKTOP);

		System.out.println(c1);
		System.out.println(c2);
		System.out.println("Equal: " + c1.equals(c2));
		
		c2.copy(c1);
		
		System.out.println(c1);
		System.out.println(c2);
		System.out.println("Equal: " + c1.equals(c2));
		
	}

}
