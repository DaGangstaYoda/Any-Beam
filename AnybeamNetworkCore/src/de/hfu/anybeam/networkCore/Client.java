package de.hfu.anybeam.networkCore;

import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;

/**
 * Represents a network Client with all necessary Information.
 * @author chrwuer
 * @since 1.0
 * @version 1.0
 */
public class Client implements Comparable<Client>, Serializable {
	
	private static final long serialVersionUID = -5823296242806470526L;
	
	//The InetAddress of this client
	private final InetAddress ADDRESS;
	private final String NAME;
	private final int DATA_PORT;
	private final String ID;
	private final String OS_NAME;
	private final DeviceType DEVICE_TYPE;
	private final String GROUP;
	private final int encryptionKeyChecksum;
	
	/**
	 * Creates a new {@link Client} instance with the given Information.
	 * @param a The client's {@link InetAddress}
	 * @param n The client's name
	 * @param dataPort The port on which the client is listening for incoming data tranmissions
	 * @param id The client's id
	 */
	public Client(InetAddress a, String n, int dataPort, String id, String osName, String group, int encryptionKeyChecksum, String deviceType) {
		this.setAddress(a);
		this.setName(n);
		this.setId(id);
		this.setDataPort(dataPort);
		this.setOsName(osName);
		this.setGroup(group);
		this.setEncryptionKeyChecksum(encryptionKeyChecksum);

		try {
			this.setDeviceType(DeviceType.valueOf(deviceType));
		} catch(Exception e) {
			e.printStackTrace();
			this.setDeviceType(DeviceType.TYPE_UNKNOWN);
		}
	}
	
	public Client(InetAddress a, String n, int dataPort, String id, String osName, String group,int encryptionKeyChecksum, DeviceType deviceType) {
		this.setAddress(a);
		this.setName(n);
		this.setId(id);
		this.setDataPort(dataPort);
		this.setOsName(osName);
		this.setDeviceType(deviceType);
		this.setGroup(group);
		this.setEncryptionKeyChecksum(encryptionKeyChecksum);
	}
	
	/**
	 * Returns the client's {@link InetAddress}.
	 * @return The client's {@link InetAddress}
	 */
	public InetAddress getAddress() {
		return ADDRESS;
	}
	
	/**
	 * Sets the client's {@link InetAddress}.
	 * @param address The new {@link InetAddress} for the client
	 */
	public void setAddress(InetAddress address) {
		this.ADDRESS = address;
	}
	
	/**
	 * Returns the client's name.
	 * @return The client's name.
	 */
	public String getName() {
		return NAME;
	}
	
	/**                                                              
	 * Sets the client's name.         
	 * @param ADDRESS The new name for the client     
	 */                                                              
	public void setName(String name) {
		this.NAME = name;
	}
	
	/**
	 * Returns the port on which the client is listening for incoming data transmissions.
	 * @return The port on which the client is listening for incoming data transmissions
	 */
	public void setDataPort(int dataPort) {
		this.DATA_PORT = dataPort;
	}
	
	/**                                                              
	 * Sets the port on which the client is listening for incoming data transmissions.                        
	 * @param ADDRESS The new port on which the client is listening for incoming data transmissions   
	 */                                                              
	public int getDataPort() {
		return this.DATA_PORT;
	}
	
	/**
	 * Returns the client's id.
	 * @return The client's id
	 */
	public void setId(String id) {
		this.ID = id;
	}
	
	/**                                                              
	 * Sets the client's id.                        
	 * @param ADDRESS The new id for the client     
	 */                                                              
	public String getId() {
		return this.ID;
	}
	
	/**
	 * Returns the client's id.
	 * @return The client's id
	 */
	public void setOsName(String osName) {
		this.OS_NAME = osName;
	}
	
	/**                                                              
	 * Sets the client's id.                        
	 * @param ADDRESS The new id for the client     
	 */                                                              
	public String getOsName() {
		return this.OS_NAME;
	}
	
	public void setDeviceType(DeviceType deviceType) {
		this.DEVICE_TYPE = deviceType;
	}
	
	public DeviceType getDeviceType() {
		return this.DEVICE_TYPE;
	}
	
	public String getGroup() {
		return GROUP;
	}

	public void setGroup(String group) {
		this.GROUP = group;
	}

	public int getEncryptionKeyChecksum() {
		return encryptionKeyChecksum;
	}

	public void setEncryptionKeyChecksum(int encryptionKeyChecksum) {
		this.encryptionKeyChecksum = encryptionKeyChecksum;
	}
	
	public boolean isEncryptionKeyCompatible() {
		return this.isEncryptionKeyCompatible(NetworkCoreUtils.getNetworkEnvironmentSettings(this.getGroup()));
	}
	
	public boolean isEncryptionKeyCompatible(NetworkEnvironmentSettings settings) {
		if(settings == null)
			throw new IllegalArgumentException("The given NetworkEnvironmentSettings are null. "
					+ "This can happen if the corresponding NetworkEnvironment for this Client's group "
					+ "is not registered at NetworkCoreUtils. In this case you can provide a individual set of "
					+ "NetworkEnvironmentSettings invoking sendData(InputStream, inputStreamSize, sourceName, "
					+ "NetworkEnvironmentSettings). ");
		
		return settings.getEncryptionKeyChecksum() == this.getEncryptionKeyChecksum();
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
	
	public void sendData(InputStream in, long inputStreamSize, String sourceName) 
			throws Exception {
		this.sendData(in, inputStreamSize, sourceName, NetworkCoreUtils.getNetworkEnvironmentSettings(this.getGroup()));
	}
	
	public void sendData(InputStream in, long inputStreamSize, String sourceName, NetworkEnvironmentSettings settings) 
			throws Exception {
		
		if(settings == null)
			throw new IllegalArgumentException("The given NetworkEnvironmentSettings are null. "
					+ "This can happen if the corresponding NetworkEnvironment for this Client's group "
					+ "is not registered at NetworkCoreUtils. In this case you can provide a individual set of "
					+ "NetworkEnvironmentSettings invoking sendData(InputStream, inputStreamSize, sourceName, "
					+ "NetworkEnvironmentSettings). ");
		
		new DataSender(in, inputStreamSize, sourceName, settings.getEncryptionType(), settings.getEncryptionKey(), this.getDataPort(), this.getAddress())
			.startTransmission();;

	}

}
