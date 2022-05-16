package audiopaket;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlay extends Thread
{
	private Controller controllerM;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	//private String temp = "Test.wav";
	//hier test mit ChinGong.wav
	private String temp = "ChinGong.wav";

	public AudioPlay(ControllerM controller)
	{
		this.controllerM = controllerM;
	}

	@Override
	public void run()
	{
		try
		{
			//hier test mit ChinGong.wav
			File file = new File(temp);
			audioStream = AudioSystem.getAudioInputStream(file);
			audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			Clip clip = (Clip) AudioSystem.getLine(info);

			clip.open(audioStream);
			clip.start();
		}
		catch (UnsupportedAudioFileException e )
		{
			System.out.println("AudioFormat nicht unterstützt" + e);
		}
		catch (IOException e )
		{
			System.out.println("IOException" + e);
		}
		catch (LineUnavailableException e )
		{
			System.out.println("LineUnavailableException" + e);
		}

		catch (Exception e )
		{
			System.out.println("Exception e " + e);
		}
		/*
		 * finally { if ((audioStream != null)) { try {
		 * 
		 * audioStream.close(); } catch (IOException e ) {
		 * System.out.println("IOException-Close()" + e); } } }
		 */
	}

}
