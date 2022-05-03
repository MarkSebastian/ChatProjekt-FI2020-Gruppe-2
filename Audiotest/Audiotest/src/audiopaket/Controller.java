package audiopaket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
//test
//hallo dominik
public class Controller
{
	private Gui gui;
	private AudioInputStream inputStream;
	AudioFormat adFormat;
	TargetDataLine targetDataLine;
	
	public Controller()
	{
		gui=new Gui();
		setButtons();
	}
	
	//brauchen wir das oder erst bei Client
		/*private ObjectInputStream in; //object streams to/from client
	    private ObjectOutputStream out;*/
	
	private void setButtons()
	{
		gui.setBtnAbspielen(e -> playAudio());
		gui.setBtnAufnehmen(e -> recordAudio());
			
	}
	
	/*public void audioRecord() throws LineUnavailableException
	{
		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		
		if(! AudioSystem.isLineSupported(info)) {
			System.out.println("Line is not supported");
		}
		
		TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
		targetDataLine.open();
		
	}*/
	
	
	private void recordAudio()
	{
		 try 
		 {
		    	/*
		    	 * targetdataline allows data to be read in byte streams
		    	 */
		        adFormat = getAudioFormat();
		        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
		        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
		        targetDataLine.open(adFormat);
		        targetDataLine.start();

		        /*Thread captureThread = new Thread(new CaptureThread());
		        /*
		         * Start event of the thread begins or ceases active presentation of the data
		         */
		        //captureThread.start();
		    } catch (Exception e) {
		    	/*
		    	 * event handler is used in case of any byte requirements
		    	 */
		        StackTraceElement stackEle[] = e.getStackTrace();
		        for (StackTraceElement val : stackEle) {
		            System.out.println(val);
		        }
		        System.exit(0);
		   }
	}
	
	
	private AudioFormat getAudioFormat() 
	{	
		
		float sampleRate = 16000.0F;
	    int sampleInbits = 16;
	    //mono
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = false;
	    return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
	}
	

	public void playAudio()
	{
		System.out.println("Test");
	
	}

	
}
