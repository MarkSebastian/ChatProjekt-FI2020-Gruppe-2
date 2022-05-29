package chatClient;

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
	private Control control;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private Thread thread;
	private String temp = "Test.wav";
	//hier test mit ChinGong.wav
	//private String temp = "ChinGong.wav";

	public AudioPlay(Control control)
	{
		this.control = control;
	}

	//threads in einzelnen methoden
	//brauche ich hier die parameter 
	public void playThread(AudioAufnehmen2 audioAufnehmen2)
	{
		thread = new Thread(this);
		thread.setName("PlayAudio");
		//System.out.println("In AudioAufnehmen2 Thread Name "+thread.getName());
		thread.start();
	}
	
	public void stoppeThread()
	{
		thread = null;
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
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			
			Clip clip = (Clip) AudioSystem.getLine(info);
		
			clip.open(audioStream);
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
		finally {
			//this.
		}
	}

}