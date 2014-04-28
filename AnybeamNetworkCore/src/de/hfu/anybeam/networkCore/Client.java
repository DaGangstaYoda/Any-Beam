package de.hfu.anybeam.networkCore;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;


/**
 * Represents a network Client with all necessary Information.
 * @author chrwuer
 * @since 1.0
 */
public class Client implements Comparable<Client>, Serializable {
	
	private static final long serialVersionUID = -5823296242806470526L;
	private InetAddress address;
	private String name;
	private int dataPort;
	private String id;
	
	/**
	 * Creates a new {@link Client} instance with the given Information.
	 * @param a The client's {@link InetAddress}
	 * @param n The client's name
	 * @param dataPort The port on which the client is listening for incoming data tranmissions
	 * @param id The client's id
	 */
	public Client(InetAddress a, String n, int dataPort, String id) {
		this.setAddress(a);
		this.setName(n);
		this.setId(id);
		this.setDataPort(dataPort);
	}
	
	/**
	 * Returns the client's {@link InetAddress}.
	 * @return The client's {@link InetAddress}
	 */
	public InetAddress getAddress() {
		return address;
	}
	
	/**
	 * Sets the client's {@link InetAddress}.
	 * @param address The new {@link InetAddress} for the client
	 */
	public void setAddress(InetAddress address) {
		this.address = address;
	}
	
	/**
	 * Returns the client's name.
	 * @return The client's name.
	 */
	public String getName() {
		return name;
	}
	
	/**                                                              
	 * Sets the client's name.         
	 * @param address The new name for the client     
	 */                                                              
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the port on which the client is listening for incoming data transmissions.
	 * @return The port on which the client is listening for incoming data transmissions
	 */
	public void setDataPort(int dataPort) {
		this.dataPort = dataPort;
	}
	
	/**                                                              
	 * Sets the port on which the client is listening for incoming data transmissions.                        
	 * @param address The new port on which the client is listening for incoming data transmissions   
	 */                                                              
	public int getDataPort() {
		return this.dataPort;
	}
	
	/**
	 * Returns the client's id.
	 * @return The client's id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**                                                              
	 * Sets the client's id.                        
	 * @param address The new id for the client     
	 */                                                              
	public String getId() {
		return this.id;
	}
	
	/**
	 * Copys all values from the given source {@link Client}.
	 * @param source The {@link Client} to copy all values from.
	 */
	public void copy(Client source) {
		try {
			Field[] fields = Client.class.getDeclaredFields();
			
			for(Field f : fields) {
				if(!Modifier.isFinal(f.getModifiers())) 
					f.set(this, f.get(source));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	@Override
	public String toString() {
		try {
			StringBuilder out = new StringBuilder("{");
			Field[] fields = Client.class.getDeclaredFields();
			
			for(Field f : fields) {
				if(!Modifier.isStatic(f.getModifiers()))
					if(f.getType().isPrimitive())
						out.append(String.format("%s:%s, ", f.getName(), f.get(this)));
					else
						out.append(String.format("%s:\"%s\", ", f.getName(), f.get(this)));
				
			}
			
			return out.append("}").delete(out.length()-3, out.length()-1).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return "{Error while creating String}";
		}	
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Client)) 
			return false;
		
		Client c = (Client) obj;
		
		try {
			Field[] fields = Client.class.getDeclaredFields();
			
			for(Field f : fields) {
				if(!Modifier.isStatic(f.getModifiers()))
					if(!f.get(this).equals(f.get(c)))
						return false;
				
			}
			
			return true;
			
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int compareTo(Client o) {
		return this.getName().compareTo(o.getName());
	}
	

}