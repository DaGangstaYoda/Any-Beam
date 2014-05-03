package de.hfu.anybeam.networkCore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * Used by {@link DataReceiver} to handle incoming connections.
 * @author chrwuer
 * @version 1.0
 * @since 1.0
 */
class DataReceiverConnection extends AbstractTransmission {
	
	//The InputStream from the socket
	private final InputStream INPUT;
	
	//the adapter to inform about status updates
	private final DataReceiverAdapter ADAPTER;
	
	//the encryption type used
	private final EncryptionType ENCRYPTION_TYPE;
	
	//the encryption key used
	private final byte[] ENCRYPTION_KEY;
	
	//the OuttpuStream to write the received data in
	private	OutputStream transmissionOutput = null;

	/**
	 * Creates a new {@link DataReceiverConnection} instance using the given settings.
	 * @param in the {@link InputStream} to read from
	 * @param encryptionType the used {@link EncryptionType}
	 * @param encryptionKey the used encryption key
	 * @param adapter the {@link DataReceiverAdapter} to get the {@link OutputStream} from and inform about progress updates
	 */
	public DataReceiverConnection(InputStream in, EncryptionType encryptionType, 
			byte[] encryptionKey, DataReceiverAdapter adapter) {
		super(adapter);
		//save args
		this.INPUT = in;
		this.ENCRYPTION_TYPE = encryptionType;
		this.ADAPTER = adapter;
		this.ENCRYPTION_KEY = encryptionKey;
		
	}
	
	@Override
	public void transmit() throws Exception {
		
		//Create CiperInputStream if necessary, else just use INPUT
		InputStream transmissionInput = null;
		if(this.ENCRYPTION_TYPE != EncryptionType.NONE) {
			//Create cipher
			Cipher c = EncryptionUtils.createCipher(this.ENCRYPTION_TYPE);
			SecretKeySpec k = EncryptionUtils.createKey(this.ENCRYPTION_TYPE, this.ENCRYPTION_KEY);
			c.init(Cipher.DECRYPT_MODE, k);	
			
			//Create cipher input Stream
			transmissionInput = new CipherInputStream(this.INPUT, c);
			
		} else {
			//Use default input
			transmissionInput = this.INPUT;
			
		}
					
		//read header with common informations. End marked with newline
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		UrlParameterBundle header = null;
		int read = 0;
		
		while((read = transmissionInput.read()) >= 0) {
			
			//If char is not \n keep reading
			if(read != '\n'){
				bos.write(read);

			//Line end found, header complete
			} else {
				//Create bundle, close bos
				header = new UrlParameterBundle(new String(bos.toByteArray()));
				bos.close();
				
				//Set settings of AbstarctTransmission
				this.setTotalLength(header.getLong("LENGTH"));
				this.setResourceName(header.get("NAME"));
				
				//request OutpuStream from adapter
				this.transmissionOutput = this.ADAPTER.downloadStarted(this.createTransmissionEvent(null), header.get("ID"));
			
				//break the loop
				break;
			}
		}

		//copy data into OutpuStream
		byte[] buffer = new byte[1024];
		int transmittedInCurrentInterval = 0;
		while((read = transmissionInput.read(buffer)) > 0) {
			//copy and increase the counter by the count of read bytes
			transmissionOutput.write(buffer, 0, read);
			transmittedInCurrentInterval += read;
			
			//If more than 100kb are copied it's time to call the AbstarctTransmission to update the progress
			if(transmittedInCurrentInterval > 100000) {
				this.increaseTransmittedLength(transmittedInCurrentInterval);
				transmittedInCurrentInterval = 0;
			}

		}

		//flush output and close input, DO NOT CLOSE OUTPUT, that's the adapters task and may be not wished
		transmissionOutput.flush();	
		transmissionInput.close();
		
	}

	@Override
	public void forceCloseTransmissionStream() throws IOException {
		//Close input
		if(this.INPUT != null)
			this.INPUT.close();
		
		//Call adapter and request close of output
		if(this.transmissionOutput != null)
			this.ADAPTER.closeOutputStream(this.createTransmissionEvent(null), this.transmissionOutput);	
	}
	
}
