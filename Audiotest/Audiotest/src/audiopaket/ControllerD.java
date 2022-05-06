package audiopaket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
//test
public class ControllerD
{
	private Gui gui;
	private AudioInputStream inputStream;
	private AudioFormat audioFormat;
	private TargetDataLine tdl;
	private AudioFormat.Encoding encoding1 = AudioFormat.Encoding.PCM_SIGNED;
	
	
	public ControllerD()
	{
		gui=new Gui();
		setButtons();
	}
	
	private void setButtons()
	{
		gui.setBtnAbspielen(e -> audioPlay());
		gui.setBtnAufnehmen(e -> audioRecord());
	}
	
	public void audioRecord()
	{
		try {
		//In der Methode Audio Format werden die Parameter bestimmt
		audioFormat = getAudioFormatSettings();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
		tdl = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
		tdl.open(audioFormat);
		tdl.start();
		
		Thread captureThread = new Thread(new CaptureThread());
		}
		catch(Exception e)
		{
			
		}
		/*
		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		
		if(! AudioSystem.isLineSupported(info)) {
			System.out.println("Line is not supported");
		}
		
		TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
		targetDataLine.open();
		*/
		
	}
	
	private AudioFormat getAudioFormatSettings()
	{
		float sampleRate = 16000.0F;
		int sampleInbits = 16;
		//Vielleicht auch 2
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
		
	}
	
	public void audioPlay()
	{
		System.out.println("Test");
	}
	

	//bei Buttonklick einfügen
}
