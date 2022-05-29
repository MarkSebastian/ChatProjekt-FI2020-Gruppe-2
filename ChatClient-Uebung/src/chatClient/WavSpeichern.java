package chatClient;

import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioSystem;


public class WavSpeichern
{
	private int random;
	private File file;
	private Type fileFormat;

	public boolean speichereInWav(AudioInputStream ais)
	{
		if (ais == null)
		{
			return false;
		}
		random = (int) (Math.random() * 10000000 + 10000000);
		file = new File(random + ".wav");
		fileFormat = AudioFileFormat.Type.WAVE;
		try
		{
			System.out.println("Datei " + random + ".wav wurde gespeichert.");
			ais.reset();
		}
		catch (Exception e )
		{
			return false;
		}

		try
		{
			AudioSystem.write(ais, fileFormat, file);
		}
		catch (Exception ex )
		{
			return false;
		}
		return true;
	}

}