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
	private ControllerM controllerM;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private String temp = "Test.wav";
	//hier test mit ChinGong.wav
	//private String temp = "ChinGong.wav";

	public AudioPlay(ControllerM controllerM)
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
			audioFormat  = new AudioFormat(44100, 16, 2, true, true);
			//audioFormat = audioStream.getFormat();
			System.out.println(audioStream.getFormat());
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			
			System.out.println("Hier nicht 1");
			Clip clip = (Clip) AudioSystem.getLine(info);
			System.out.println("Hier nicht 2");
			/////
			//was braucht der für ein audioformat
			System.out.println(clip.getFormat());
		
			clip.open(audioStream);
			//
			System.out.println("Hier nicht 33");
			clip.start();
		
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
			//System.out.println("AudioDate <0");
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
