package audiopaket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
//test
public class Controller
{
	private Gui gui;
	private AudioInputStream inputStream;
	
	public Controller()
	{
		gui=new Gui();
		setButtons();
	}
	
	private void setButtons()
	{
		gui.setBtnAbspielen(e -> audioPlay());
		gui.setBtnAufnehmen(e -> {
			try
			{
				audioRecord();
			}
			catch (LineUnavailableException e1 )
			{
				e1.printStackTrace();
			}
		});
	}
	
	public void audioRecord() throws LineUnavailableException
	{
		AudioFormat format = new AudioFormat(16000, 8, 2, true, true);
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		
		if(! AudioSystem.isLineSupported(info)) {
			System.out.println("Line is not supported");
		}
		
		TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
		targetDataLine.open();
		
	}
	
	
	public void audioPlay()
	{
		System.out.println("Test");
	}
	

	//bei Buttonklick einfügen
}
