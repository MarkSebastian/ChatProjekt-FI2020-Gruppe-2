package audiopaket;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.HashSet;

public class CaptureThread extends Thread
{
	byte[] tempBuffer = new byte[10240];
	boolean bool = false;
	boolean stopaudioCapture = false;
	ByteArrayOutputStream bos;
	
	public void run()
	{
		bos = new ByteArrayOutputStream();
		stopaudioCapture = true;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		//Buffer ist eine öffentliche abstrakte Klasse und dient als Container für Daten eines bestimmten Datentyps
		Buffer[] free;
		
		
	}
}
