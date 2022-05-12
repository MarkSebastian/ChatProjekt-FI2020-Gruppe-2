package audiopaket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class ControllerM
{
	private Gui gui;
	private AudioInputStream inputStream;
	AudioFormat audioFormat;
	TargetDataLine tdl;
	
	public ControllerM()
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
		        audioFormat = getAudioFormat();
		        //enthält audioformate und Puffergröße, TargetDataLine liest audio data
		        //siehe Constructor Detail
		        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		        tdl = (TargetDataLine) dataLineInfo;
		        
		        //Audiosystem needed ?
		        tdl = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
		        tdl.open(audioFormat);
		        tdl.start();

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
	//kocharshaivi19

	public void playAudio()
	{
		System.out.println("Test");
	}
	
	
	
	//legit
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
	


	
}

