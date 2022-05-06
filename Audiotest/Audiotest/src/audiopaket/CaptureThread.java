package audiopaket;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import javax.sound.sampled.TargetDataLine;

public class CaptureThread extends Thread
{
	protected byte[] tempBuffer = new byte[10240];
	protected boolean bool = false;
	protected boolean stopaudioCapture = false;
	protected File file = new File("test.txt");
	protected TargetDataLine tdl;
	protected ByteArrayOutputStream bos;
	
	public void run()
	{
		bos = new ByteArrayOutputStream();
		stopaudioCapture = true;
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		//Buffer ist eine öffentliche abstrakte Klasse und dient als Container für Daten eines bestimmten Datentyps
		Buffer[] free;
		
		
		try
		{
			file.createNewFile();
			fileWriter = new FileWriter(file.getName(), true);
			bufferedWriter = new BufferedWriter(fileWriter);
			while(!stopaudioCapture)
			{
				int cnt = tdl.read(tempBuffer, 0, tempBuffer.length);
				
				if (cnt > 0)
				{
					System.out.println("Klasse Capture Thread, Run");
					Byte n = 0;
					
				}
			}
		}
		catch (IOException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
