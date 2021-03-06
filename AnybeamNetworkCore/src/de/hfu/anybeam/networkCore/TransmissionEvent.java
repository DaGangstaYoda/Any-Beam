package de.hfu.anybeam.networkCore;

/**
 * Displays the current state of an {@link AbstractTransmission} object
 * @author chrwuer
 * @since 1.0
 * @version 1.0
 */
public class TransmissionEvent {
	
	//The unique transmission id for the transmission displayed by this object
	private final int TRANSMISSON_ID;
	
	//The total length of the transmission
	private final long TOTAL_LENGTH;
	
	//The already transmitted length
	private final long TRASMITTED_LENGTH;
	
	//The transmission's resource name
	private final String RESOURCE_NAME;
	
	//The AbstractTransmission handling the transmission
	private final AbstractTransmission TRANSMISSION_HANDLER;
	
	//A Exception that occured (hopefully always null)
	private final Exception EXCEPTION;
	
	//The transmission's average spped
	private final double AVERAGE_SPEED;
	
	/**
	 * Creates a new {@link TransmissionEvent} object.
	 * @param transmissionId the unique transmission id of the transmissions
	 * @param resourceLength the transmission's total length in bytes
	 * @param transmittedLength the already transmitted length in byte
	 * @param resourceName the name of the resource transmitted
	 * @param error an {@link Exception} that may have occured or null
	 * @param averageSpeed the average speed of the transmission in Bytes/second
	 * @param handler
	 */
	TransmissionEvent(int transmissionId, long resourceLength, long transmittedLength, 
			String resourceName, Exception error, double averageSpeed, AbstractTransmission handler) {
		this.TRANSMISSON_ID = transmissionId;
		this.TOTAL_LENGTH = resourceLength;
		this.TRASMITTED_LENGTH = transmittedLength;
		this.RESOURCE_NAME = resourceName;
		this.TRANSMISSION_HANDLER = handler;
		this.AVERAGE_SPEED = averageSpeed;
		this.EXCEPTION = error;
	}

	/**
	 * Returns the unique transmission id of the transmission displayed by this event.
	 * @return the unique transmission id of the transmission displayed by this event
	 */
	public int getTransmissionId() {
		return this.TRANSMISSON_ID;
	}

	/**
	 * Returns the transmission's total length in byte.
	 * @return the transmission's total length in byte
	 */
	public long getTotalLength() {
		return this.TOTAL_LENGTH;
	}

	/**
	 * Returns the already transmitted length in byte.
	 * @return the already transmitted length in byte
	 */
	public long getTransmittedLength() {
		return this.TRASMITTED_LENGTH;
	}

	/**
	 * Returns the name of the resource transmitted.
	 * @return the name of the resource transmitted
	 */
	public String getResourceName() {
		return this.RESOURCE_NAME;
	}
	
	/**
	 * Returns the {@link AbstractTransmission} which handles the transmission.
	 * @return the {@link AbstractTransmission} which handles the transmission
	 */
	public AbstractTransmission getTransmissionHandler() {
		return this.TRANSMISSION_HANDLER;
	}
	
	/**
	 * Returns the percentage already done in range from 0 to 1.
	 * @return the percentage already done in range from 0 to 1
	 */
	public double getPercentDone() {
		return (double) this.getTransmittedLength() / (double) this.getTotalLength();
	}
	
	/**
	 * Returns a {@link Exception} that may have occured while transmitting data.
	 * @return a {@link Exception} that may have occured while transmitting data
	 */
	public Exception getException() {
		return this.EXCEPTION;
	}
	
	/**
	 * Returns if the transmision has an end or is a endess stream.
	 * @return true if the transmision has an end or is a endess stream, false otherwise
	 */
	public boolean isIndeterminate() {
		return this.getTotalLength() < 0;
	}
	
	/**
	 * Returns the calculated average speed of the transmission in Bytes/second.
	 * @return the calculated average speed of the transmission in Bytes/second
	 */
	public double getAverageSpeed() {
		return AVERAGE_SPEED;
		
	}
}
