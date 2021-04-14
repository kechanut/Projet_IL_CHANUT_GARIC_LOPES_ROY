package portailEV3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

import ca.ualberta.awhittle.ev3btrc.MessageBluetooth;
import lejos.hardware.Bluetooth;
import lejos.hardware.LocalBTDevice;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;

public class EnvoiBT {
	private static DataOutputStream dataOut; 
	private static OutputStream dataOutput;
	private static InputStream dataIn;
	private static ObjectInputStream objectIn;
	private static BTConnection BTLink;
	private static boolean app_alive;
	private static LocalBTDevice BTdevice;
	
	volatile MessageBluetooth msg;
	volatile int msgVoiture;
	
	
		public void run() {
/*		
			connect();
			app_alive = true;
			while(app_alive){
				try {
					dataOutput = BTLink.openDataOutputStream();
					if ( dataOutput.available() > 0 ) {
						objectIn = new ObjectInputStream(dataOutput);
						
						msg = (MessageBluetooth) objectIn.readObject();
						
//						msgVoiture = (int) objectIn.readInt();
						Thread.sleep(100);
												
						objectIn.close();
					}
					
					dataIn.close();
					msg = null;

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
*/			
			
		}
		
		public static void connect()
		{  
			System.out.println("En ecoute");
			BTConnector ncc = (BTConnector) Bluetooth.getNXTCommConnector();
			BTLink = (BTConnection) ncc.waitForConnection(100, NXTConnection.RAW);
			dataOut = BTLink.openDataOutputStream();
			dataIn = BTLink.openInputStream();
		}
		
		public String getBTadress() {
			BTdevice = new LocalBTDevice();
			return BTdevice.getBluetoothAddress();
		}
}
